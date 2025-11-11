package com.arqui.skateboardservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skateboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String QrCode;
    private Double totalKm;
    private Double usedTime;
    private boolean available;
    private boolean inMaintenance;

    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    private SkateboardStatus status;

    private Long stationId;

    private LocalDateTime lastUpdate;
}