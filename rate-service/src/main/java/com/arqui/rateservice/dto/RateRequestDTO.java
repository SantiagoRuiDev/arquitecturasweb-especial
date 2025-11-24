package com.arqui.rateservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateRequestDTO {
    private double rate;
    private double rate_extra_pause;
}
