package com.arqui.skateboardservice.dto;


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
    private Double lenght;
    private Long stationId;
    private LocalDateTime lastUpdate;
}