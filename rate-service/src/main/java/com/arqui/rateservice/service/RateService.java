package com.arqui.rateservice.service;

import com.arqui.rateservice.dto.RateReponseDTO;
import com.arqui.rateservice.entity.Rate;
import com.arqui.rateservice.mapper.RateMapper;
import com.arqui.rateservice.repository.RateRepository;
import org.springframework.stereotype.Service;

@Service
public class RateService {
    private final RateMapper rateMapper;
    RateRepository rateRepository;

    public RateService(RateRepository rateRepository, RateMapper rateMapper) {
        this.rateRepository = rateRepository;
        this.rateMapper = rateMapper;
    }

    public RateReponseDTO fetchActualRate() {
        Rate rate = rateRepository.findById(1).orElseThrow(() -> new RuntimeException("Rate not found"));
        return rateMapper.convertFromEntity(rate);
    }
}
