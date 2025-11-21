package com.arqui.travelservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "travels")
public class Travel {

    @Id
    private String id;

    private Long accountId;
    private Long userId;
    private Long scooterId;
    private Long startStopId;
    private Long endStopId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Double distanceKm;
    private Double cost;

    private TravelStatus status;

    private List<Pause> pauses = new ArrayList<>();
}
