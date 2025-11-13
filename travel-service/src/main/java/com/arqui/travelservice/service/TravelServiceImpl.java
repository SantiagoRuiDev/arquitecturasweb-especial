package com.arqui.travelservice.service;

import com.arqui.travelservice.dto.request.TravelRequestDTO;
import com.arqui.travelservice.dto.request.DiscountRequestDTO;
import com.arqui.travelservice.dto.request.TravelEndRequestDTO;
import com.arqui.travelservice.dto.request.ScooterUsageUpdateDTO;
import com.arqui.travelservice.dto.request.PauseRequestDTO;
import com.arqui.travelservice.dto.request.PauseEndRequestDTO;
import com.arqui.travelservice.dto.response.DiscountResultDTO;
import com.arqui.travelservice.dto.response.RateResponseDTO;
import com.arqui.travelservice.dto.response.TravelResponseDTO;
import com.arqui.travelservice.feignClient.AccountClient;
import com.arqui.travelservice.feignClient.ScooterClient;
import com.arqui.travelservice.feignClient.RateClient;
import com.arqui.travelservice.mapper.TravelMapper;
import com.arqui.travelservice.dto.ScooterUsageDTO;
import com.arqui.travelservice.dto.PauseDTO;
import com.arqui.travelservice.dto.TravelReportDTO;
import com.arqui.travelservice.domain.model.Travel;
import com.arqui.travelservice.domain.model.Pause;
import com.arqui.travelservice.domain.model.TravelStatus;
import com.arqui.travelservice.domain.repository.TravelRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelServiceImpl implements TravelService {

    private final TravelRepository travelRepository;
    private final AccountClient accountClient;
    private final ScooterClient scooterClient;
    private final RateClient rateClient;

    public TravelServiceImpl(TravelRepository travelRepository, AccountClient accountClient, ScooterClient scooterClient, RateClient rateClient) {
        this.travelRepository = travelRepository;
        this.accountClient = accountClient;
        this.scooterClient = scooterClient;
        this.rateClient = rateClient;
    }

    // C - Consultar los monopatines con mas de X viajes en un cierto año
    @Override
    public List<ScooterUsageDTO> getTopScooters(int year, int minTrips) {
        // Implementación de la consulta de los monopatines con más de X viajes en un cierto año
        return travelRepository.findTopScooters(year, minTrips);
    }

    // E - Ver los usuarios que más utilizan los monopatines, filtrado por período y por tipo de usuario
    @Override
    public List<TravelReportDTO> getUserTripsByPeriodAndType(LocalDateTime startDate, LocalDateTime endDate, Long userType) {
        return travelRepository.findUserTripsByPeriodAndType(startDate, endDate, userType);
    }

    /* -------------------------------------------------------------------------------------------------------- */
    
    // Inicia un nuevo travel
    @Override
    public TravelResponseDTO startTravel(TravelRequestDTO request) {
        Travel travel = TravelMapper.fromRequestDTO(request);

        // Validar que el usuario y el scooter existen usando los clientes feign
        if(accountClient.getUserById(travel.getUserId()) == null || scooterClient.getScooterById(travel.getScooterId()) == null) {
            throw new RuntimeException("Usuario no encontrado en el account service");
        }

        travel.setStatus(TravelStatus.STARTED);
        return TravelMapper.toDTO(travelRepository.save(travel));
    }

    // Pausa el travel
    @Override
    public TravelResponseDTO pauseTravel(PauseRequestDTO request) {
        Travel travel = travelRepository.findById(request.getTravelId())
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        // Crear y guardar la pausa
        Pause pause = new Pause();
        pause.setStartPause(request.getStartPause());
        pause.setTravel(travel);
        travel.setStatus(TravelStatus.PAUSED);
        travel.getPauses().add(pause);
        travelRepository.save(travel);

        return TravelMapper.toDTO(travel);
    }

    // Finaliza una pausa
    @Override
    public TravelResponseDTO resumePause(PauseEndRequestDTO request) {

        Pause pause = new Pause();
        pause.setId(request.getPauseId());
        pause.setEndPause(request.getEndPause());
        pause.setExceededTimeLimit(request.isExceededTimeLimit());
        
        // Buscar el viaje que contiene esta pausa
        List<Travel> travels = travelRepository.findAll();
        
        Travel travelWithPause = travels.stream()
            .filter(t -> t.getPauses().stream().anyMatch(p -> p.getId().equals(request.getPauseId())))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Pausa no encontrada"));
        
        // Actualizar la pausa
        Pause pauseToUpdate = travelWithPause.getPauses().stream()
            .filter(p -> p.getId().equals(request.getPauseId()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Pausa no encontrada en el viaje"));
        
        pauseToUpdate.setEndPause(request.getEndPause());
        pauseToUpdate.setExceededTimeLimit(request.isExceededTimeLimit());
        
        travelRepository.save(travelWithPause);
        return TravelMapper.toDTO(travelWithPause);
    }

    // Terminar el travel
    @Override
    public TravelResponseDTO endTravel(TravelEndRequestDTO request) {
        Travel travel = travelRepository.findById(request.getTravelId())
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        travel.setEndStopId(request.getEndStopId());
        travel.setEndTime(request.getEndTime());
        travel.setDistanceKm(request.getDistanceKm());
        travel.setStatus(TravelStatus.FINISHED);

        // Obtener tarifa actual desde rate service usando el rate client
        RateResponseDTO rateResponse = rateClient.fetchActualRate();

        // Calcular costo
        Double travelCost = request.getDistanceKm() * rateResponse.getRate();
        travel.setCost(travelCost);

        // Descontar el costo del viaje de la cuenta del usuario usando el account client
        DiscountRequestDTO discountReq = new DiscountRequestDTO(travelCost);
        DiscountResultDTO discountResult = accountClient.discount(travel.getUserId(), discountReq);

        if (discountResult == null) {
            throw new RuntimeException("No se pudo aplicar el descuento en la cuenta del usuario.");
        }

        // Actualizar el kilometraje y las pausas del scooter
        List<PauseDTO> pauseDTOs = travel.getPauses().stream()
                .map(pause -> new PauseDTO(pause.getId(), pause.getStartPause(), pause.getEndPause(), pause.isExceededTimeLimit()))
                .collect(Collectors.toList());
        
        // Envia un ScooterUsageUpdateDTO al scooter service para actualizar su estado luego de q finalice el viaje
        ScooterUsageUpdateDTO scooterUsageUpdate = new ScooterUsageUpdateDTO(request.getDistanceKm(), pauseDTOs);
        scooterClient.updateScooterUsage(travel.getScooterId(), scooterUsageUpdate);

        Travel saved = travelRepository.save(travel);
        return TravelMapper.toDTO(saved);
    }

    // Obtener un viaje por su ID
    @Override
    public TravelResponseDTO getTravelById(Long id) {
        return travelRepository.findById(id)
                .map(TravelMapper::toDTO) // Mapear a DTO
                .orElseThrow(() -> new RuntimeException("La id proporcionada no corresponde a ningún viaje existente"));
    }

    @Override
    public List<TravelResponseDTO> getAllTravels() {
        return travelRepository.findAll().stream().map(t -> {
            TravelResponseDTO dto = new TravelResponseDTO();
            dto.setId(t.getId());
            dto.setStartTime(t.getStartTime());
            dto.setEndTime(t.getEndTime());
            dto.setDistanceKm(t.getDistanceKm());
            dto.setCost(t.getCost());
            return dto;
        }).toList();
    }
}
