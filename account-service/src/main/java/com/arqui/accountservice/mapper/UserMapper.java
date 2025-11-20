package com.arqui.accountservice.mapper;

import com.arqui.accountservice.dto.response.UserResponseDTO;
import com.arqui.accountservice.entity.Account;
import com.arqui.accountservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO convertFromEntity(User entity){
        UserResponseDTO dto = new UserResponseDTO();
        dto.setAccounts(entity.getAccounts().stream().map(Account::getId).toList());
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setFirstname(entity.getFirstname());
        dto.setLastname(entity.getLastname());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());

        return dto;
    }
}