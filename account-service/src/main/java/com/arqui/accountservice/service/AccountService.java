package com.arqui.accountservice.service;

import com.arqui.accountservice.dto.request.AccountRequestDTO;
import com.arqui.accountservice.dto.request.DiscountRequestDTO;
import com.arqui.accountservice.dto.request.RechargeRequestDTO;
import com.arqui.accountservice.dto.request.StatusUpdateRequestDTO;
import com.arqui.accountservice.dto.response.AccountResponseDTO;
import com.arqui.accountservice.dto.response.DiscountResultDTO;
import com.arqui.accountservice.dto.response.RechargeResultDTO;
import com.arqui.accountservice.entity.Account;
import com.arqui.accountservice.entity.AccountType;
import com.arqui.accountservice.feignClients.PaymentFeignClient;
import com.arqui.accountservice.mapper.AccountMapper;
import com.arqui.accountservice.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        Account ac = new Account();

        if(req.getPaymentMethodId() == null){
            throw new IllegalArgumentException("El campo paymentMethodId es obligatorio");
        }
        if(req.getType() != AccountType.BASIC && req.getType() != AccountType.PREMIUM){
            throw  new IllegalArgumentException("El campo type es obligatorio");
        }
        if(req.getAuthMethodId() == null){
            throw new IllegalArgumentException("El campo authMethodId es obligatorio");
        }
        if(accountRepository.findByAuthMethodId(req.getAuthMethodId()).isPresent()){
            throw new IllegalArgumentException("Ya existe una cuenta registrada con este metodo de acceso");
        }

        ac.setActive(true);
        ac.setType(req.getType());
        ac.setCredits(0.00);
        ac.setPaymentMethodId(req.getPaymentMethodId());
        ac.setCreatedAt(new Date());
        ac.setAuthMethodId(req.getAuthMethodId());
        accountRepository.save(ac);
        return accountMapper.convertFromEntity(ac);
    }


    @Transactional
    public RechargeResultDTO recharge (Long id, RechargeRequestDTO req) {
        Account ac = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe una cuenta con este identificador"));
        RechargeResultDTO res = paymentClient.charge(ac.getPaymentMethodId(), req);

        if(res.isCharged()){
            ac.setCredits(ac.getCredits() + res.getAmount());
            accountRepository.save(ac);
        }

        return res;
    }

    @Transactional
    public DiscountResultDTO discount(Long id, DiscountRequestDTO req) {

        Account ac = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe una cuenta con este identificador"));

        DiscountResultDTO res = new DiscountResultDTO();
        double amount = req.getAmount();

        // Caso 1: El usuario tiene créditos suficientes
        if (ac.getCredits() >= amount) {

            ac.setCredits(ac.getCredits() - amount);
            accountRepository.save(ac);

            res.setDiscounted(true);
            res.setAmount(amount);
            res.setInfo("Se descontaron " + amount + " créditos correctamente.");
            return res;
        }

        // Caso 2: NO tiene créditos -> se intenta recargar y luego descontar
        RechargeRequestDTO dto = new RechargeRequestDTO();
        dto.setAmount(amount);

        RechargeResultDTO recharge;
        try {
            recharge = paymentClient.charge(ac.getPaymentMethodId(), dto);
        } catch (Exception e) {
            res.setDiscounted(false);
            res.setAmount(amount);
            res.setInfo("Error al contactar servicio de pago: " + e.getMessage());
            return res;
        }

        if (!recharge.isCharged()) {
            res.setDiscounted(false);
            res.setAmount(amount);
            res.setInfo("No se pudo realizar el cobro externo.");
            return res;
        }

        // Recarga exitosa: se suman créditos y luego se descuenta
        ac.setCredits(ac.getCredits() + amount);
        ac.setCredits(ac.getCredits() - amount); // jeje

        accountRepository.save(ac);

        res.setDiscounted(true);
        res.setAmount(amount);
        res.setInfo("Se realizó un cobro externo y se descontaron los créditos correctamente.");
        return res;
    }


    public AccountResponseDTO setStatus(Long id, StatusUpdateRequestDTO req) {
        Account ac = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe una cuenta con este identificador"));
        ac.setActive(req.isStatus());
        accountRepository.save(ac);

        return accountMapper.convertFromEntity(ac);
    }

    public AccountResponseDTO findById(Long id) {
        Account ac = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe una cuenta con este identificador"));
        return accountMapper.convertFromEntity(ac);
    }

    public AccountResponseDTO findByAuthMethodId(Long id) {
        Account ac = accountRepository.findByAuthMethodId(id).orElseThrow(() -> new IllegalArgumentException("No existe una cuenta con este identificador"));
        return accountMapper.convertFromEntity(ac);
    }

    public List<AccountResponseDTO> findAll(Optional<AccountType> type) {
        if(type.isPresent()) {
            return accountRepository.findAllByType(type.get()).stream().map(accountMapper::convertFromEntity).toList();
        }
        return accountRepository.findAll().stream().map(accountMapper::convertFromEntity).toList();
    }

    public boolean delete(Long id){
        Account e = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe una cuenta con este identificador"));
        accountRepository.delete(e);
        return true;
    }
}

