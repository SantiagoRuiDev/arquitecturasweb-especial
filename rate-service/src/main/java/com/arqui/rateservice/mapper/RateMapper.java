package com.arqui.rateservice.mapper;

import com.arqui.rateservice.dto.RateResponseDTO;
import com.arqui.rateservice.entity.Rate;
import org.springframework.stereotype.Component;

@Component
public class RateMapper {
    public RateResponseDTO convertFromEntity(Rate entity){
        RateResponseDTO dto = new RateResponseDTO();
        dto.setRate(entity.getRate());
        dto.setRate_extra_pause(entity.getRate_extra_pause());

        return dto;
    }
}
