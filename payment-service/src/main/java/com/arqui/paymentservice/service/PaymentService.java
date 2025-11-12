package com.arqui.paymentservice.service;

import com.arqui.paymentservice.dto.request.PaymentChargeRequestDTO;
import com.arqui.paymentservice.dto.request.PaymentMethodRequestDTO;
import com.arqui.paymentservice.dto.response.PaymentMethodResponseDTO;
import com.arqui.paymentservice.dto.response.PaymentChargeResultDTO;
import com.arqui.paymentservice.entity.PaymentMethod;
import com.arqui.paymentservice.mapper.PaymentMethodMapper;
import com.arqui.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentMethodMapper paymentMethodMapper;
    PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository, PaymentMethodMapper paymentMethodMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMethodMapper = paymentMethodMapper;
    }

    public PaymentMethodResponseDTO save (PaymentMethodRequestDTO req) {
        PaymentMethod pm = new PaymentMethod(
                null,
                req.getName(), req.getOwner_document(), req.getOwner_firstname(), req.getOwner_lastname(), req.getActive(), req.getFunds()
        );

        paymentRepository.save(pm);
        return paymentMethodMapper.convertFromEntity(pm);
    }


    public PaymentChargeResultDTO charge (Long identifier, PaymentChargeRequestDTO req) {
        PaymentMethod pm = paymentRepository.findById(identifier).orElseThrow(() -> new IllegalArgumentException("No existe un metodo de pago con este identificador"));
        PaymentChargeResultDTO res =  new PaymentChargeResultDTO();
        res.setAmount(req.getAmount());

        if(pm.getFunds() > 0 && req.getAmount() < pm.getFunds()) {
            pm.setFunds(pm.getFunds() - req.getAmount());
            paymentRepository.save(pm);
            res.setCharged(true);
            res.setInfo("Cobro efectuado efectivamente a traves de " + pm.getName());
        } else {
            res.setInfo("Saldo insuficiente para realizar cobro a traves de " + pm.getName());
            res.setCharged(false);
        }

        return res;
    }

    public PaymentMethodResponseDTO findById (Long identifier) {
        PaymentMethod pm = paymentRepository.findById(identifier).orElseThrow(() -> new IllegalArgumentException("No existe un metodo de pago con este identificador"));

        return paymentMethodMapper.convertFromEntity(pm);
    }

    public boolean delete (Long identifier) {
        PaymentMethod pm = paymentRepository.findById(identifier).orElseThrow(() -> new IllegalArgumentException("No existe un metodo de pago con este identificador"));
        paymentRepository.delete(pm);
        return true;
    }
}
