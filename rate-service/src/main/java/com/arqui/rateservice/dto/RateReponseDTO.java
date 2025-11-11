package com.arqui.rateservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateReponseDTO {
    private Integer rate;
    private Integer rate_extra_pause;
}
