package com.arqui.travelservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arqui.travelservice.domain.model.Pause;

public interface PauseRepository extends JpaRepository<Pause, Long> {
}