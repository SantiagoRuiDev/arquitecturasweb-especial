package com.arqui.travelservice.dto.request;

import com.arqui.travelservice.dto.PauseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScooterUsageUpdateDTO {
    private Double kilometrageKm;
    private List<PauseDTO> pauses;

    //a modo de prueba
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long pauseMinutes;
}