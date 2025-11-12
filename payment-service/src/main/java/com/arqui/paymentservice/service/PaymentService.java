package com.arqui.paymentservice.service;

import com.arqui.paymentservice.dto.request.PaymentChargeRequestDTO;
import com.arqui.paymentservice.dto.request.PaymentMethodRequestDTO;
import com.arqui.paymentservice.dto.response.PaymentMethodResponseDTO;
import com.arqui.paymentservice.dto.response.PaymentChargeResultDTO;
import com.arqui.paymentservice.entity.PaymentBill;
import com.arqui.paymentservice.entity.PaymentMethod;
import com.arqui.paymentservice.mapper.PaymentMethodMapper;
import com.arqui.paymentservice.repository.PaymentBillRepository;
import com.arqui.paymentservice.repository.PaymentMethodRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class PaymentService {
    private final PaymentMethodMapper paymentMethodMapper;
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentBillRepository paymentBillRepository;

    public PaymentService(PaymentMethodRepository paymentMethodRepository, PaymentBillRepository paymentBillRepository, PaymentMethodMapper paymentMethodMapper) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentBillRepository = paymentBillRepository;
        this.paymentMethodMapper = paymentMethodMapper;
    }

    public PaymentMethodResponseDTO save (PaymentMethodRequestDTO req) {
        PaymentMethod pm = new PaymentMethod(
                null,
                req.getName(), req.getOwner_document(), req.getOwner_firstname(), req.getOwner_lastname(), req.getActive(), req.getFunds(), null
        );

        paymentMethodRepository.save(pm);
        return paymentMethodMapper.convertFromEntity(pm);
    }


    @Transactional
    public PaymentChargeResultDTO charge (Long identifier, PaymentChargeRequestDTO req) {
        PaymentMethod pm = paymentMethodRepository.findById(identifier).orElseThrow(() -> new IllegalArgumentException("No existe un metodo de pago con este identificador"));
        PaymentChargeResultDTO res =  new PaymentChargeResultDTO();
        res.setAmount(req.getAmount());

        if(pm.getFunds() > 0 && req.getAmount() < pm.getFunds()) {
            pm.setFunds(pm.getFunds() - req.getAmount());
            PaymentBill bill =  new PaymentBill();
            bill.setPaymentMethod(pm);
            bill.setTotal(req.getAmount());
            bill.setIssueDate(new Date());
            paymentBillRepository.save(bill);
            paymentMethodRepository.save(pm);
            res.setCreatedAt(new Date());
            res.setCharged(true);
            res.setInfo("Cobro efectuado efectivamente a traves de " + pm.getName());
        } else {
            res.setInfo("Saldo insuficiente para realizar cobro a traves de " + pm.getName());
            res.setCharged(false);
        }

        return res;
    }

    public PaymentMethodResponseDTO findById (Long identifier) {
        PaymentMethod pm = paymentMethodRepository.findById(identifier).orElseThrow(() -> new IllegalArgumentException("No existe un metodo de pago con este identificador"));

        return paymentMethodMapper.convertFromEntity(pm);
    }

    public boolean delete (Long identifier) {
        PaymentMethod pm = paymentMethodRepository.findById(identifier).orElseThrow(() -> new IllegalArgumentException("No existe un metodo de pago con este identificador"));
        paymentMethodRepository.delete(pm);
        return true;
    }
}
