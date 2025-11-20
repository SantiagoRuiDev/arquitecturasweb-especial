package com.arqui.travelservice.dto.request;


import com.arqui.travelservice.entity.SkateboardStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScooterRequestDTO {
    private String qr;
    private Double totalKm;
    private Double usedTime;
    private Double pausedTime;
    private boolean available;
    private boolean inMaintenance;
    private Double latitude;
    private Double longitude;
    private Long stationId;
    private SkateboardStatus status;
}