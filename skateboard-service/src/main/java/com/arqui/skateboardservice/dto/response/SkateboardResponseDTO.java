package com.arqui.skateboardservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkateboardResponseDTO {
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
    private String status;
    private LocalDateTime lastUpdate;
}