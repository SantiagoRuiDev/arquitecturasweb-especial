package com.arqui.travelservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScooterUsageDTO {
    private Long scooterId;
    private Long tripCount;
}