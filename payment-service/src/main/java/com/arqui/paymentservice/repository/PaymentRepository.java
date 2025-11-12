package com.arqui.paymentservice.repository;

import com.arqui.paymentservice.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentMethod, Long> {
}
