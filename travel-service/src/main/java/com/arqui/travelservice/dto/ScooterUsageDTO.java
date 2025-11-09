package com.arqui.travelservice.dto;

public class ScooterUsageDTO {
    private Long scooterId;
    private int tripCount;

    public Long getScooterId() {
        return scooterId;
    }

    public void setScooterId(Long scooterId) {
        this.scooterId = scooterId;
    }

    public int getTripCount() {
        return tripCount;
    }

    public void setTripCount(int tripCount) {
        this.tripCount = tripCount;
    }
}
