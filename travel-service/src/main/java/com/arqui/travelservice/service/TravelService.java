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
    // A - reporte de uso por KM / pausas
    TravelReportDTO getTravelReport(Long id);
    // C - Consultar los monopatines con mas de X viajes en un cierto año
    List<ScooterUsageDTO> getTopScooters(int year, int minTrips);
    // E - Consultar los viajes de un usuario en un cierto año
    List<TravelReportDTO> getUserTripsByYear(Long userId, int year);
    // D - Consultar los viajes en un cierto rango de tiempo
    List<TravelReportDTO> getTripsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}
