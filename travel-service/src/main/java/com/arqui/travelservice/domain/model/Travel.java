package com.arqui.travelservice.domain.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
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

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Double distanceKm;
    private Double cost;

    @Enumerated(EnumType.STRING)
    private TravelStatus status;

    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pause> pauses = new ArrayList<>();

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

    public List<Pause> getPauses() {
        return pauses;
    }

    public void setPauses(List<Pause> pauses) {
        this.pauses = pauses;
    }


}