package com.arqui.travelservice.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import com.arqui.travelservice.entity.TravelStatus;
import com.arqui.travelservice.dto.PauseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelResponseDTO {
    private Long id;
    private Long accountId;
    private Long userId;
    private Long scooterId;
    private Long startStopId;
    private Long endStopId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double distanceKm;
    private Double cost;
    private TravelStatus status;
    private List<PauseDTO> pauses;

}