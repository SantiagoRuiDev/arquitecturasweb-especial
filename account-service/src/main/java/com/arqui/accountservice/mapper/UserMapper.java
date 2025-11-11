package com.arqui.accountservice.mapper;

import com.arqui.accountservice.dto.response.UserResponseDTO;
import com.arqui.accountservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO convertFromEntity(User entity){
        UserResponseDTO dto = new UserResponseDTO();
        dto.setFirstname(entity.getFirstname());
        dto.setLastname(entity.getLastname());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());

        return dto;
    }
}