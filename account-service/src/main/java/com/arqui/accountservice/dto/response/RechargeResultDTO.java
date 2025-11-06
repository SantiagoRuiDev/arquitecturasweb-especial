package com.arqui.accountservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RechargeResultDTO {
    private Date createdAt;
    private Integer amount;
    private boolean charged;
    private String info;
}
