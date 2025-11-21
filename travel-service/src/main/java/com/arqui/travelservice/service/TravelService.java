package com.arqui.travelservice.service;

import com.arqui.travelservice.dto.request.TravelRequestDTO;
import com.arqui.travelservice.dto.request.TravelEndRequestDTO;
import com.arqui.travelservice.dto.response.TravelResponseDTO;
import com.arqui.travelservice.dto.ScooterUsageDTO;
import com.arqui.travelservice.dto.TravelReportDTO;
import com.arqui.travelservice.dto.response.UserScooterUsageDTO;
import com.arqui.travelservice.entity.AccountType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TravelService {
    TravelResponseDTO startTravel(TravelRequestDTO request);
    TravelResponseDTO endTravel(TravelEndRequestDTO request);
    TravelResponseDTO pauseTravel(String id);
    TravelResponseDTO resumePause(String id);
    TravelResponseDTO getTravelById(String id);
    List<TravelResponseDTO> getAllTravels();

    // C - Consultar los monopatines con mas de X viajes en un cierto año
    List<ScooterUsageDTO> getTopScooters(int year, int minTrips);

    // E - Ver los usuarios que más utilizan los monopatines, filtrado por período y por tipo de usuario
    List<TravelReportDTO> getUserTripsByPeriodAndType(LocalDateTime startDate, LocalDateTime endDate, AccountType userType);

    // H - Como usuario quiero saber cuánto he usado los monopatines en un período, y opcionalmente si otros usuarios relacionados a mi cuenta los han usado.
    List<UserScooterUsageDTO> getScootersUsageByUser(Long userId, LocalDateTime startDate, LocalDateTime endDate, boolean includeRelatedUsers);
}
