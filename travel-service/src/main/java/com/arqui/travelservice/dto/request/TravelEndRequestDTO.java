package com.arqui.travelservice.dto.request;
import java.time.LocalDateTime;

// DTO para la solicitud de fin de viaje

public class TravelEndRequestDTO {
    private Long travelId;
    private Long endStopId;
    private LocalDateTime endTime;
    private Double distanceKm;

    // Getters y Setters

    public Long getTravelId() {
        return travelId;
    }
    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }
    public Long getEndStopId() {
        return endStopId;
    }
    public void setEndStopId(Long endStopId) {
        this.endStopId = endStopId;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public Double getDistanceKm() {
        return distanceKm;
    }
    public void setDistanceKm(Double distanceKm) {
        this.distanceKm = distanceKm;
    }

}
