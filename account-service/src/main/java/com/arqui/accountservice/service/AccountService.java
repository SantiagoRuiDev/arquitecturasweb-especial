package com.arqui.accountservice.service;

import com.arqui.accountservice.dto.request.AccountRequestDTO;
import com.arqui.accountservice.dto.request.DiscountRequestDTO;
import com.arqui.accountservice.dto.request.RechargeRequestDTO;
import com.arqui.accountservice.dto.response.AccountResponseDTO;
import com.arqui.accountservice.dto.response.DiscountResultDTO;
import com.arqui.accountservice.dto.response.RechargeResultDTO;
import com.arqui.accountservice.entity.Account;
import com.arqui.accountservice.feignClients.PaymentFeignClient;
import com.arqui.accountservice.mapper.AccountMapper;
import com.arqui.accountservice.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PaymentFeignClient paymentClient;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper, PaymentFeignClient paymentClient) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.paymentClient = paymentClient;
    }

    public AccountResponseDTO save (AccountRequestDTO req) {
        Account ac = new Account(null, null, req.getType(), 0.00, true, null, req.getPaymentAccountId());
        accountRepository.save(ac);
        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        return accountMapper.convertFromEntity(ac);
    }


    @Transactional
    public RechargeResultDTO recharge (Long id, RechargeRequestDTO req) {
        Account ac = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe una cuenta con este identificador"));
        RechargeResultDTO res = paymentClient.charge(ac.getPaymentAccountId(), req);

        if(res.isCharged()){
            ac.setCredits(ac.getCredits() + res.getAmount());
            accountRepository.save(ac);
        }

        return res;
    }

    @Transactional
    public DiscountResultDTO discount (Long id, DiscountRequestDTO req) {
        Account ac = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe una cuenta con este identificador"));
        DiscountResultDTO res = new DiscountResultDTO();

        if(ac.getCredits() > req.getAmount()){
            ac.setCredits(ac.getCredits() - req.getAmount());
            accountRepository.save(ac);
        } else {
            RechargeRequestDTO dto = new RechargeRequestDTO();
            dto.setAmount(req.getAmount());
            RechargeResultDTO recharge = paymentClient.charge(ac.getPaymentAccountId(), dto);

            if(recharge.isCharged()){
                ac.setCredits(ac.getCredits() + res.getAmount());
                ac.setCredits(ac.getCredits() - res.getAmount());
                accountRepository.save(ac);
            } else {
                res.setDiscounted(false);
                res.setAmount(req.getAmount());
                res.setInfo("No se pudo descontar la cantidad de creditos " + req.getAmount() + " correctamente.");
            }
        }

        res.setDiscounted(true);
        res.setAmount(req.getAmount());
        res.setInfo("Se le desconto la cantidad de creditos " + req.getAmount() + " correctamente.");

        return res;
    }

    public AccountResponseDTO setStatus(Long id, Boolean status) {
        Account ac = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe una cuenta con este identificador"));
        ac.setActive(status);
        accountRepository.save(ac);

        return accountMapper.convertFromEntity(ac);
    }

    public AccountResponseDTO findById(Long id) {
        Account ac = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe una cuenta con este identificador"));
        return accountMapper.convertFromEntity(ac);
    }

    public boolean delete(Long id){
        Account e = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe una cuenta con este identificador"));
        accountRepository.delete(e);
        return true;
    }
}

