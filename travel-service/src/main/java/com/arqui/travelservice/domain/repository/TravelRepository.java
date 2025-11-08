package com.arqui.travelservice.domain.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.arqui.travelservice.domain.model.Travel;

public interface TravelRepository extends JpaRepository<Travel, Long> {
}