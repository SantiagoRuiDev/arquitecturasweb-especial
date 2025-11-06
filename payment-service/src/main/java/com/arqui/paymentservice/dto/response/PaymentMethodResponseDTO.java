package com.arqui.paymentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodResponseDTO {
    private String name;
    private String owner_firstname;
    private String owner_lastname;
    private boolean active;
}
