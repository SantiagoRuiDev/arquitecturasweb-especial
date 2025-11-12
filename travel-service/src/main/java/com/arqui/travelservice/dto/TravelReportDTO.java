package com.arqui.travelservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelReportDTO {
    private Long userId;
    private Long tripCount;
    private Double totalDistance;
}