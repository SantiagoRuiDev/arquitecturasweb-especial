package com.arqui.paymentservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // Por ejemplo "MercadoPago"
    private Integer owner_document; // Datos del dueño
    private String owner_firstname; // Datos del dueño
    private String owner_lastname; // Datos del dueño
    private boolean active; // El estado de la cuenta y/o metodo de pago agregado
    private Double funds; // Seria el saldo de la cuenta, lo usamos para simular debitos y cobros (NO SON LOS CREDITOS)

    @OneToMany(mappedBy = "paymentMethod", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<PaymentBill> bills;
}
