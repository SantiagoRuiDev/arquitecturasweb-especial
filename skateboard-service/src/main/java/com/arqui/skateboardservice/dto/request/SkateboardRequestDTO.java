package com.arqui.skateboardservice.dto.request;


import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkateboardRequestDTO {
    private String QrCode;
    private Double totalKm;
    private Double usedTime;
    private boolean available;
    private boolean inMaintenance;
    private Double latitude;
    private Double longitude;
    private Long stationId;
    private LocalDateTime lastUpdate;
}