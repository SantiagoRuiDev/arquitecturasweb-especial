package com.arqui.travelservice.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Pause {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startPause;
    private LocalDateTime endPause;
    private boolean exceededTimeLimit;

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

    public Travel getTravel() {
        return travel;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    private Travel travel;

    // Getters y Setters
}

