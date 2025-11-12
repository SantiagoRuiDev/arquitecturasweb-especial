package com.arqui.accountservice.mapper;

import com.arqui.accountservice.dto.request.AccountRequestDTO;
import com.arqui.accountservice.dto.response.AccountResponseDTO;
import com.arqui.accountservice.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public static Account convertFromDTO(AccountRequestDTO dto){
        return new Account();
    }

    public AccountResponseDTO convertFromEntity(Account entity){
        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setIsPremium(entity.isActive());
        dto.setCredits(entity.getCredits());

        return dto;
    }
}