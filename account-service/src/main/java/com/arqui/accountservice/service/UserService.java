package com.arqui.accountservice.service;

import com.arqui.accountservice.dto.response.UserResponseDTO;
import com.arqui.accountservice.entity.User;
import com.arqui.accountservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO findById(Integer id) {
        User us = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe una cuenta con este identificador"));
        return userMapper.convertFromEntity(us);
    }
}
