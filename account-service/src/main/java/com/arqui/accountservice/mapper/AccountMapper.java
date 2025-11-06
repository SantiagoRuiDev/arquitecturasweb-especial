package com.arqui.accountservice.mapper;

import com.arqui.accountservice.dto.request.AccountRequestDTO;
import com.arqui.accountservice.dto.response.AccountResponseDTO;
import com.arqui.accountservice.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public static Account convertFromDTO(AccountRequestDTO dto){
        return new Account(dto.getId(), dto.getCreatedAt(), dto.getIsPremium(), dto.getCredits(), null, dto.getPaymentAccountId());
    }

    public AccountResponseDTO convertFromEntity(Account entity){
        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setIsPremium(entity.getIsPremium());
        dto.setCredits(entity.getCredits());

        return dto;
    }
}