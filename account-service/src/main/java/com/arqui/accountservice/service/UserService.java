package com.arqui.accountservice.service;

import com.arqui.accountservice.dto.response.UserResponseDTO;
import com.arqui.accountservice.entity.User;
import com.arqui.accountservice.mapper.UserMapper;
import com.arqui.accountservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService (UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDTO findById(Integer id) {
        User us = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe una cuenta con este identificador"));
        return userMapper.convertFromEntity(us);
    }
}
