package com.arqui.travelservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountResultDTO {
    private Integer amount;
    private boolean discounted;
    private String info;
}
