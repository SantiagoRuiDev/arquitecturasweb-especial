package com.arqui.travelservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arqui.travelservice.entity.Pause;

public interface PauseRepository extends JpaRepository<Pause, Long> {
}