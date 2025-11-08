package com.arqui.travelservice.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import com.arqui.travelservice.domain.model.TravelStatus;
import com.arqui.travelservice.dto.PauseDTO;

// DTO para la respuesta de un viaje

public class TravelResponseDTO {
    private Long id;
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
    private List<PauseDTO> pauses;

    // Getters and Setters
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getAccountId() {
        return accountId;
    }
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getScooterId() {
        return scooterId;
    }
    public void setScooterId(Long scooterId) {
        this.scooterId = scooterId;
    }
    public Long getStartStopId() {
        return startStopId;
    }
    public void setStartStopId(Long startStopId) {
        this.startStopId = startStopId;
    }
    public Long getEndStopId() {
        return endStopId;
    }
    public void setEndStopId(Long endStopId) {
        this.endStopId = endStopId;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
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
    public Double getCost() {
        return cost;
    }
    public void setCost(Double cost) {
        this.cost = cost;
    }
    public TravelStatus getStatus() {
        return status;
    }
    public void setStatus(TravelStatus status) {
        this.status = status;
    }
    public List<PauseDTO> getPauses() {
        return pauses;
    }
    public void setPauses(List<PauseDTO> pauses) {
        this.pauses = pauses;
    }

}