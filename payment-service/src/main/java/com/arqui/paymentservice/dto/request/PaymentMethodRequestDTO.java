package com.arqui.paymentservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodRequestDTO {
    private String name;
    private Integer owner_document;
    private String owner_firstname;
    private String owner_lastname;
    private Boolean active;
    private Integer funds;
}
