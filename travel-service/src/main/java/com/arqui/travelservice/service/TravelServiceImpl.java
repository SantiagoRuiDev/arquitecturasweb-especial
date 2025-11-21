package com.arqui.travelservice.service;

import com.arqui.travelservice.dto.request.*;
import com.arqui.travelservice.dto.response.*;
import com.arqui.travelservice.entity.*;
import com.arqui.travelservice.feignClient.AccountClient;
import com.arqui.travelservice.feignClient.ScooterClient;
import com.arqui.travelservice.feignClient.RateClient;
import com.arqui.travelservice.mapper.TravelMapper;
import com.arqui.travelservice.dto.ScooterUsageDTO;
import com.arqui.travelservice.dto.PauseDTO;
import com.arqui.travelservice.dto.TravelReportDTO;
import com.arqui.travelservice.repository.TravelRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
        return null;//travelRepository.findTopScooters(year, minTrips);
    }

    // E - Ver los usuarios que más utilizan los monopatines, filtrado por período y por tipo de usuario
    @Override
    public List<TravelReportDTO> getUserTripsByPeriodAndType(LocalDateTime startDate, LocalDateTime endDate, AccountType userType) {
        List<AccountResponseDTO> accounts = accountClient.findAllByType(userType);
        List<TravelReportDTO> travelsInPeriod = null;//travelRepository.findTripsByPeriod(startDate, endDate);

        if (accounts.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> accountIds = accounts.stream()
                .map(AccountResponseDTO::getId)
                .toList();

        travelsInPeriod.removeIf(t -> !accountIds.contains(t.getAccountId()));

        return travelsInPeriod;
    }

    // H - Como usuario quiero saber cuánto he usado los monopatines en un período, y opcionalmente si otros usuarios relacionados a mi cuenta los han usado.
    @Override
    public List<UserScooterUsageDTO> getScootersUsageByUser(Long userId, LocalDateTime startDate, LocalDateTime endDate, boolean includeRelatedUsers) {
        if(includeRelatedUsers){
            UserResponseDTO user = accountClient.getUserById(userId);
            return null;//travelRepository.findScooterUsageByAccount(startDate, endDate, user.getAccounts());
        } else {
            return null;//.findScooterUsageByUser(startDate, endDate, userId);
        }
    }

    /* -------------------------------------------------------------------------------------------------------- */
    
    // Inicia un nuevo travel
    public TravelResponseDTO startTravel(TravelRequestDTO request) {
        Travel travel = TravelMapper.fromRequestDTO(request);
        ScooterRequestDTO dto = new ScooterRequestDTO();
        // Validar que el usuario y el scooter existen usando los clientes feign
        try {
            UserResponseDTO userFetched = accountClient.getUserById(travel.getUserId());
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        try {
            ScooterResponseDTO scooter = scooterClient.getScooterById(travel.getScooterId());
            if(scooter.isInMaintenance()){
                throw new IllegalArgumentException("El scooter se encuentra en mantenimiento.");
            }
            if(!scooter.getStatus().equals(SkateboardStatus.AT_STATION)){
                throw new IllegalArgumentException("El scooter no se encuentra en una parada.");
            }
            if(!scooter.getStationId().equals(request.getStartStopId())){
                throw new IllegalArgumentException("El scooter no se encuentra en la parada de inicio.");
            }
            // Completo el DTO para actualizar la información del monopatin
            dto.setStationId(scooter.getStationId());
            dto.setLatitude(scooter.getLatitude());
            dto.setLongitude(scooter.getLongitude());
            dto.setTotalKm(scooter.getTotalKm());
            dto.setAvailable(scooter.isAvailable());
            dto.setInMaintenance(scooter.isInMaintenance());
            dto.setPausedTime(scooter.getPausedTime());
            dto.setUsedTime(scooter.getUsedTime());
            dto.setQr(scooter.getQrCode());
            dto.setTotalKm(scooter.getTotalKm());
            dto.setStatus(SkateboardStatus.USED);
        } catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }

        travel.setStatus(TravelStatus.STARTED);
        scooterClient.update(travel.getScooterId(), dto);
        return TravelMapper.toDTO(travelRepository.save(travel));
    }

    // Pausa el travel
    public TravelResponseDTO pauseTravel(String id) {
        Travel travel = travelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        // Crear y guardar la pausa
        Pause pause = new Pause();
        pause.setId(UUID.randomUUID().toString());
        pause.setStartPause(LocalDateTime.now());
        travel.setStatus(TravelStatus.PAUSED);
        travel.getPauses().add(pause);
        travelRepository.save(travel);

        return TravelMapper.toDTO(travel);
    }

    // Finaliza una pausa
    public TravelResponseDTO resumePause(String id) {
        Pause pause = new Pause();
        pause.setId(id);
        pause.setEndPause(LocalDateTime.now());
        pause.setExceededTimeLimit(false);
        
        // Buscar el viaje que contiene esta pausa
        List<Travel> travels = travelRepository.findAll();
        
        Travel travelWithPause = travels.stream()
            .filter(t -> t.getPauses().stream().anyMatch(p -> p.getId().equals(id)))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Pausa no encontrada"));
        
        // Actualizar la pausa
        Pause pauseToUpdate = travelWithPause.getPauses().stream()
            .filter(p -> p.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Pausa no encontrada en el viaje"));

        if(!travelWithPause.getStatus().equals(TravelStatus.PAUSED)) {
            throw new IllegalArgumentException("Este viaje no se encuentra pausado");
        }
        
        pauseToUpdate.setEndPause(LocalDateTime.now());
        pauseToUpdate.setExceededTimeLimit(false);

        travelWithPause.setStatus(TravelStatus.RESUMED);
        
        travelRepository.save(travelWithPause);
        return TravelMapper.toDTO(travelWithPause);
    }

    // Terminar el travel
    @Override
    public TravelResponseDTO endTravel(TravelEndRequestDTO request) {
        Travel travel = travelRepository.findById(request.getTravelId())
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        travel.setEndStopId(request.getEndStopId());
        travel.setEndTime(LocalDateTime.now());
        travel.setDistanceKm(request.getDistanceKm());
        travel.setStatus(TravelStatus.FINISHED);

        // Obtener tarifa actual desde rate service usando el rate client
        RateResponseDTO rateResponse = rateClient.fetchActualRate();

        // Calcular costo
        Double travelCost = request.getDistanceKm() * rateResponse.getRate();
        travel.setCost(travelCost);

        // Descontar el costo del viaje de la cuenta del usuario usando el account client
        DiscountRequestDTO discountReq = new DiscountRequestDTO(travelCost);
        DiscountResultDTO discountResult = accountClient.discount(travel.getAccountId(), discountReq);

        if (discountResult == null) {
            throw new RuntimeException("No se pudo aplicar el descuento en la cuenta del usuario.");
        }

        // Actualizar el kilometraje y las pausas del scooter
        List<PauseDTO> pauses = travel.getPauses().stream()
                .map(pause -> new PauseDTO(pause.getId(), pause.getStartPause(), pause.getEndPause(), pause.isExceededTimeLimit()))
                .collect(Collectors.toList());
        
        // Envia un ScooterUsageUpdateDTO al scooter service para actualizar su estado luego de q finalice el viaje
        ScooterUsageUpdateDTO scooterUsageUpdate = new ScooterUsageUpdateDTO(travel.getStartTime(), travel.getEndTime(), travel.getDistanceKm(), pauses);
        scooterClient.updateScooterUsage(travel.getScooterId(), scooterUsageUpdate);

        scooterClient.update(travel.getScooterId(), new ScooterStatusRequestDTO(SkateboardStatus.AT_STATION));

        Travel saved = travelRepository.save(travel);
        return TravelMapper.toDTO(saved);
    }

    // Obtener un viaje por su ID
    public TravelResponseDTO getTravelById(String id) {
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
