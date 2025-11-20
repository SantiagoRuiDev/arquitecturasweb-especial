package com.arqui.paymentservice.mapper;

import com.arqui.paymentservice.dto.request.PaymentMethodRequestDTO;
import com.arqui.paymentservice.dto.response.PaymentMethodResponseDTO;
import com.arqui.paymentservice.entity.PaymentMethod;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodMapper {
    public static PaymentMethod convertFromDTO(PaymentMethodRequestDTO dto){
        return new PaymentMethod();
    }

    public PaymentMethodResponseDTO convertFromEntity(PaymentMethod entity){
        PaymentMethodResponseDTO dto = new PaymentMethodResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setOwner_firstname(entity.getOwner_firstname());
        dto.setOwner_lastname(entity.getOwner_lastname());
        dto.setActive(entity.isActive());

        return dto;
    }
}
