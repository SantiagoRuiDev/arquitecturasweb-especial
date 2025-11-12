package com.arqui.accountservice.dto.request;

import com.arqui.accountservice.entity.AccountType;
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
    private AccountType type;
    private Long paymentAccountId;
}
