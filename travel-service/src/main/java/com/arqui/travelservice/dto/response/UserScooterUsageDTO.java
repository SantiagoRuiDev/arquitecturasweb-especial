package com.arqui.travelservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserScooterUsageDTO {
    private Long scooterId;
    private Long userId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double distance;
    private Long tripCount;
}