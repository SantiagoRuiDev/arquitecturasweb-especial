package com.arqui.travelservice.dto.request;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelEndRequestDTO {
    private String travelId;
    private Long endStopId;
    private Double distanceKm;
}
