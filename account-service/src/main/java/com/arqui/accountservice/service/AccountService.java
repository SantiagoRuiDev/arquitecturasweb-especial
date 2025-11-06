package com.arqui.accountservice.service;

import com.arqui.accountservice.dto.request.AccountRequestDTO;
import com.arqui.accountservice.dto.response.AccountResponseDTO;
import com.arqui.accountservice.entity.Account;
import com.arqui.accountservice.mapper.AccountMapper;
import com.arqui.accountservice.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public AccountResponseDTO save (AccountRequestDTO accountRequestDTO) {
        Account ac = new Account();
        accountRepository.save(ac);
        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        return accountMapper.convertFromEntity(ac);
    }

    public AccountResponseDTO findById(Integer id) {
        Account ac = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe una cuenta con este identificador"));
        return accountMapper.convertFromEntity(ac);
    }

    public boolean delete(Integer id){
        Account e = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe una cuenta con este identificador"));
        accountRepository.delete(e);
        return true;
    }
}

