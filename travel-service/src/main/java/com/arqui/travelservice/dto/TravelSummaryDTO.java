package com.arqui.travelservice.dto;

import java.time.LocalDateTime;

public class TravelSummaryDTO {
    private Long id;
    private String userName;
    private String scooterCode;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double distanceKm;
    private Double cost;

    // Getters y Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getScooterCode() {
        return scooterCode;
    }
    public void setScooterCode(String scooterCode) {
        this.scooterCode = scooterCode;
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

}