package com.arqui.travelservice.dto.request;

import java.time.LocalDateTime;

// DTO para la solicitud de inicio de viaje

public class TravelRequestDTO {
    private Long accountId;           // Cuenta asociada (básica o premium)
    private Long userId;              // Usuario que inicia el viaje
    private Long scooterId;           // Monopatín escaneado por qr
    private Long startStopId;         // Parada donde se inicia el viaje (Hay q ver si se cambia)
    private LocalDateTime startTime;  // Puede asignarse automáticamente en el backend

    // Getters y Setters

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
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

}