package com.arqui.paymentservice.controller;

import com.arqui.paymentservice.dto.request.PaymentChargeRequestDTO;
import com.arqui.paymentservice.dto.request.PaymentMethodRequestDTO;
import com.arqui.paymentservice.dto.response.PaymentChargeResultDTO;
import com.arqui.paymentservice.dto.response.PaymentMethodResponseDTO;
import com.arqui.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment-method")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("")
    public ResponseEntity<PaymentMethodResponseDTO> save (@RequestBody PaymentMethodRequestDTO req) {
        PaymentMethodResponseDTO res =  paymentService.save(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/charge/{id}")
    public ResponseEntity<PaymentChargeResultDTO> charge (@PathVariable Integer id, @RequestBody PaymentChargeRequestDTO req) {
        PaymentChargeResultDTO res =  paymentService.charge(id, req);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodResponseDTO> findById (@PathVariable Integer id) {
        PaymentMethodResponseDTO res =  paymentService.findById(id);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Integer id) {
        boolean deleted =  paymentService.delete(id);
        if(deleted){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
