package com.arqui.accountservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountRequestDTO {
    private Date createdAt;
    private Boolean isPremium;
    private Integer credits;
    private Integer paymentAccountId;
}
