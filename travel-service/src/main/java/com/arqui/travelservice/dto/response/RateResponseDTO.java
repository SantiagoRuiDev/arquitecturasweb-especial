package com.arqui.travelservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateResponseDTO {
    private Integer rate;
    private Integer rate_extra_pause;
}
