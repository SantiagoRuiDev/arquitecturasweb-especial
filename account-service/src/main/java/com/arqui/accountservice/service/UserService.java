package com.arqui.accountservice.service;

import com.arqui.accountservice.dto.request.UserRequestDTO;
import com.arqui.accountservice.dto.response.UserResponseDTO;
import com.arqui.accountservice.entity.Account;
import com.arqui.accountservice.entity.User;
import com.arqui.accountservice.mapper.UserMapper;
import com.arqui.accountservice.repository.AccountRepository;
import com.arqui.accountservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AccountRepository accountRepository;

    public UserService (UserRepository userRepository, UserMapper userMapper, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public UserResponseDTO save(UserRequestDTO req) {
        Account acc = accountRepository.findById(req.getAccountId()).orElseThrow(() -> new IllegalArgumentException("No existe una cuenta con este identificador"));
        if(acc.isActive()){
            User row = new User();
            row.setUsername(req.getUsername());
            row.setFirstname(req.getFirstname());
            row.setLastname(req.getLastname());
            row.setEmail(req.getEmail());
            row.setPhone(req.getPhone());
            acc.getUsers().add(row);
            userRepository.save(row);
            accountRepository.save(acc);
            return userMapper.convertFromEntity(row);
        } else {
            throw new IllegalArgumentException("Esta cuenta no esta habilitada");
        }
    }

    public boolean delete(Long id) {
        User us = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe un usuario con este identificador"));
        userRepository.delete(us);
        return true;
    }

    public UserResponseDTO findById(Long id) {
        User us = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe un usuario con este identificador"));
        return userMapper.convertFromEntity(us);
    }
}
