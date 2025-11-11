package com.arqui.rateservice.repository;

import com.arqui.rateservice.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<Rate, Integer> {
}
