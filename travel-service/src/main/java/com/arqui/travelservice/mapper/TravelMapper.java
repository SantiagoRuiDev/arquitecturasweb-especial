package com.arqui.travelservice.mapper;

import com.arqui.travelservice.domain.model.Travel;
import com.arqui.travelservice.domain.model.TravelStatus;
import java.util.stream.Collectors;
import com.arqui.travelservice.dto.PauseDTO;
import com.arqui.travelservice.dto.response.TravelResponseDTO;
import com.arqui.travelservice.dto.request.TravelRequestDTO;

// Mapper class to convert between Travel entity and DTOs
public class TravelMapper {

    // Convert Travel entity to TravelResponseDTO
    public static TravelResponseDTO toDTO(Travel travel) {
        TravelResponseDTO dto = new TravelResponseDTO();
        dto.setId(travel.getId());
        dto.setAccountId(travel.getAccountId());
        dto.setUserId(travel.getUserId());
        dto.setScooterId(travel.getScooterId());
        dto.setStartStopId(travel.getStartStopId());
        dto.setEndStopId(travel.getEndStopId());
        dto.setStartTime(travel.getStartTime());
        dto.setEndTime(travel.getEndTime());
        dto.setDistanceKm(travel.getDistanceKm());
        dto.setCost(travel.getCost());
        dto.setStatus(travel.getStatus());
        dto.setPauses(
            travel.getPauses().stream()
                .map(p -> {
                    PauseDTO pauseDTO = new PauseDTO();
                    pauseDTO.setId(p.getId());
                    pauseDTO.setStartPause(p.getStartPause());
                    pauseDTO.setEndPause(p.getEndPause());
                    pauseDTO.setExceededTimeLimit(p.isExceededTimeLimit());
                    return pauseDTO;
                }).collect(Collectors.toList())
        );
        return dto;
    }
    
    // Convert TravelRequestDTO to Travel entity
    public static Travel fromRequestDTO(TravelRequestDTO dto) {
        Travel travel = new Travel();
        travel.setAccountId(dto.getAccountId());
        travel.setUserId(dto.getUserId());
        travel.setScooterId(dto.getScooterId());
        travel.setStartStopId(dto.getStartStopId());
        travel.setStartTime(dto.getStartTime());
        travel.setStatus(TravelStatus.STARTED);
        return travel;
    }
}