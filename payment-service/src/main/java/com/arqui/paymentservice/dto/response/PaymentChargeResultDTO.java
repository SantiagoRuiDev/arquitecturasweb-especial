package com.arqui.paymentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentChargeResultDTO {
    private Date createdAt;
    private Integer amount;
    private boolean charged;
    private String info;
}
