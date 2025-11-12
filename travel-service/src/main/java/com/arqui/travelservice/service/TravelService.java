package com.arqui.travelservice.service;

import com.arqui.travelservice.dto.request.TravelRequestDTO;
import com.arqui.travelservice.dto.request.TravelEndRequestDTO;
import com.arqui.travelservice.dto.response.TravelResponseDTO;
import com.arqui.travelservice.dto.ScooterUsageDTO;
import com.arqui.travelservice.dto.TravelReportDTO;
import java.time.LocalDateTime;
import java.util.List;

public interface TravelService {
    TravelResponseDTO startTravel(TravelRequestDTO request);
    TravelResponseDTO endTravel(TravelEndRequestDTO request);
    TravelResponseDTO getTravelById(Long id);
    List<TravelReportDTO> getAllTravels();

    // C - Consultar los monopatines con mas de X viajes en un cierto año
    List<ScooterUsageDTO> getTopScooters(int year, int minTrips);

    // E - Ver los usuarios que más utilizan los monopatines, filtrado por período y por tipo de usuario
    List<TravelReportDTO> getUserTripsByPeriodAndType(LocalDateTime startDate, LocalDateTime endDate, Long userType);
}
