package com.arqui.travelservice.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Account ID, User ID y ScooterID se obtienen de los servicios correspondientes
    // Por ejemplo, del account service y skateboard service (Habria que cambiarle el nombre porque no son skates sino scooters)
    private Long accountId; 
    private Long userId;
    private Long scooterId;
    private Long startStopId;
    private Long endStopId;
    private Long userType;


    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Double distanceKm;
    private Double cost; // Costo del viaje

    @Enumerated(EnumType.STRING)
    private TravelStatus status;

    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pause> pauses = new ArrayList<>();

}