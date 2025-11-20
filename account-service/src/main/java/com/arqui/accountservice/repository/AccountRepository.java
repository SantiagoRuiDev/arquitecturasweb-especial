package com.arqui.accountservice.repository;

import com.arqui.accountservice.entity.Account;
import com.arqui.accountservice.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByType(AccountType type);
}
