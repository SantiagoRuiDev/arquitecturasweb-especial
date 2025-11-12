package com.arqui.accountservice.dto.response;

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
public class AccountResponseDTO {
    private Date createdAt;
    private AccountType type;
    private Double credits;
}
