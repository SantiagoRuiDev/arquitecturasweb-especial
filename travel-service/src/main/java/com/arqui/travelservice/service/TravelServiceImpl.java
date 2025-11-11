package com.arqui.travelservice.service;

import com.arqui.travelservice.dto.request.TravelRequestDTO;
import com.arqui.travelservice.dto.request.DiscountRequestDTO;
import com.arqui.travelservice.dto.request.TravelEndRequestDTO;
import com.arqui.travelservice.dto.response.DiscountResultDTO;
import com.arqui.travelservice.dto.response.RateResponseDTO;
import com.arqui.travelservice.dto.response.TravelResponseDTO;
import com.arqui.travelservice.feignClient.AccountClient;
import com.arqui.travelservice.feignClient.ScooterClient;
import com.arqui.travelservice.feignClient.RateClient;
import com.arqui.travelservice.mapper.TravelMapper;
import com.arqui.travelservice.dto.ScooterUsageDTO;
import com.arqui.travelservice.dto.TravelReportDTO;
import com.arqui.travelservice.domain.model.Travel;
import com.arqui.travelservice.domain.model.TravelStatus;
import com.arqui.travelservice.domain.repository.TravelRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    // A - reporte de uso por KM / pausas
    @Override
    public TravelReportDTO getTravelReport(Long id) {
        // Implementación del reporte de uso por KM / pausas
        return travelRepository.findById(id)
                .map(travel -> {
                    TravelReportDTO report = new TravelReportDTO();
                    report.setId(travel.getId());
                    report.setDistanceKm(travel.getDistanceKm());
                    // Hay que checkear lo de las paussas
                    // report.setPauseCount(travel.getPauseCount());
                    return report;
                })
                .orElseThrow(() -> new RuntimeException("Travel not found"));
    }

    // C - Consultar los monopatines con mas de X viajes en un cierto año
    @Override
    public List<ScooterUsageDTO> getTopScooters(int year, int minTrips) {
        // Implementación de la consulta de los monopatines con más de X viajes en un cierto año
        return travelRepository.findTopScooters(year, minTrips);
    }

    // E - Consultar los viajes de un usuario en un cierto año
    @Override
    public List<TravelReportDTO> getUserTripsByYear(Long userId, int year) {
        // Implementación de la consulta de los viajes de un usuario en un cierto año

        
        if (userId == null || year <= 0) {
            throw new IllegalArgumentException("Debes proporcionar un ID de usuario o un año mayor a 0");
        }
        return travelRepository.findUserTripsByYear(userId, year);
    }

    // D - Consultar los viajes en un cierto rango de tiempo
    @Override
    public List<TravelReportDTO> getTripsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        // Implementación de la consulta de los viajes en un cierto rango de tiempo
        return travelRepository.findTripsByDateRange(startDate, endDate);
    }

    /* -------------------------------------------------------------------------------------------------------- */
    @Override
    public TravelResponseDTO startTravel(TravelRequestDTO request) {
        Travel travel = TravelMapper.fromRequestDTO(request);
        return TravelMapper.toDTO(travelRepository.save(travel));
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
        DiscountRequestDTO discountReq = new DiscountRequestDTO((int)Math.ceil(travelCost));
        DiscountResultDTO discountResult = accountClient.discount(Math.toIntExact(travel.getUserId()), discountReq);

        if (discountResult == null) {
            throw new RuntimeException("No se pudo aplicar el descuento en la cuenta del usuario.");
        }

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
    public List<TravelReportDTO> getAllTravels() {
        return travelRepository.findAll().stream().map(t -> {
            TravelReportDTO dto = new TravelReportDTO();
            dto.setId(t.getId());
            dto.setStartTime(t.getStartTime());
            dto.setEndTime(t.getEndTime());
            dto.setDistanceKm(t.getDistanceKm());
            dto.setCost(t.getCost());
            return dto;
        }).toList();
    }

}
