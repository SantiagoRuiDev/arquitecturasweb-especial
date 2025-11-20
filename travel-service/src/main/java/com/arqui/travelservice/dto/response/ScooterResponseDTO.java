package com.arqui.travelservice.dto.response;

import com.arqui.travelservice.entity.SkateboardStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScooterResponseDTO {
    private Long id;
    private String qrCode;
    private Double totalKm;
    private Double usedTime;
    private Double pausedTime;
    private boolean available;
    private boolean inMaintenance;
    private Long stationId;
    private Double latitude;
    private Double longitude;
    private SkateboardStatus status;
    private LocalDateTime lastUpdate;
}