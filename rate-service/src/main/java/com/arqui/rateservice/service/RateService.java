package com.arqui.rateservice.service;

import com.arqui.rateservice.dto.RateRequestDTO;
import com.arqui.rateservice.dto.RateResponseDTO;
import com.arqui.rateservice.entity.Rate;
import com.arqui.rateservice.mapper.RateMapper;
import com.arqui.rateservice.repository.RateRepository;
import org.springframework.stereotype.Service;

@Service
public class RateService {
    private final RateMapper rateMapper;
    private final RateRepository rateRepository;

    public RateService(RateRepository rateRepository, RateMapper rateMapper) {
        this.rateRepository = rateRepository;
        this.rateMapper = rateMapper;
    }

    // Si ya se creo una tarifa previamente se actualiza, caso contrario se crea una nueva
    public RateResponseDTO save(RateRequestDTO req) {
        Rate alreadyExist = rateRepository.findById(1).orElse(null);

        if(req.getRate() <= 0 || req.getRate() > 100) {
            throw new IllegalArgumentException("El valor de la tarifa basica debe estar entre 0 o 100");
        }
        if(req.getRate_extra_pause() <=  0 || req.getRate_extra_pause() > 100) {
            throw new IllegalArgumentException("El valor de la tarifa extra debe estar entre 0 o 100");
        }

        if(alreadyExist != null) {
            alreadyExist.setRate(req.getRate());
            alreadyExist.setRate_extra_pause(req.getRate_extra_pause());
            rateRepository.save(alreadyExist);
            return rateMapper.convertFromEntity(alreadyExist);
        } else {
            Rate rate = new Rate();
            rate.setRate(req.getRate());
            rate.setRate_extra_pause(req.getRate_extra_pause());
            rateRepository.save(rate);
            return rateMapper.convertFromEntity(rate);
        }
    }


    // Actualizar como administrador la tarifa
    public RateResponseDTO update(RateRequestDTO req) {
        Rate alreadyExist = rateRepository.findById(1).orElseThrow(() -> new IllegalArgumentException("No existe una tarifa para los viajes"));
        alreadyExist.setRate(req.getRate());
        alreadyExist.setRate_extra_pause(req.getRate_extra_pause());
        rateRepository.save(alreadyExist);
        return rateMapper.convertFromEntity(alreadyExist);
    }

    public RateResponseDTO fetchActualRate() {
        Rate rate = rateRepository.findById(1).orElseThrow(() -> new RuntimeException("No existe una tarifa para los viajes"));
        return rateMapper.convertFromEntity(rate);
    }
}
