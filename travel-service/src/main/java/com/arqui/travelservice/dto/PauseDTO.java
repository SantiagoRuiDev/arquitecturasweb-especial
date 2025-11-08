package com.arqui.travelservice.dto;

import java.time.LocalDateTime;

public class PauseDTO {
    private Long id;
    private LocalDateTime startPause;
    private LocalDateTime endPause;
    private boolean exceededTimeLimit;

    // Getters y Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getStartPause() {
        return startPause;
    }
    public void setStartPause(LocalDateTime startPause) {
        this.startPause = startPause;
    }
    public LocalDateTime getEndPause() {
        return endPause;
    }
    public void setEndPause(LocalDateTime endPause) {
        this.endPause = endPause;
    }
    public boolean isExceededTimeLimit() {
        return exceededTimeLimit;
    }
    public void setExceededTimeLimit(boolean exceededTimeLimit) {
        this.exceededTimeLimit = exceededTimeLimit;
    }
}