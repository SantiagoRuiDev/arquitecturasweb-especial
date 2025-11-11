package com.arqui.rateservice.mapper;

import com.arqui.rateservice.dto.RateReponseDTO;
import com.arqui.rateservice.entity.Rate;
import org.springframework.stereotype.Component;

@Component
public class RateMapper {
    public RateReponseDTO convertFromEntity(Rate entity){
        RateReponseDTO dto = new RateReponseDTO();
        dto.setRate(entity.getRate());
        dto.setRate_extra_pause(entity.getRate_extra_pause());

        return dto;
    }
}
