package com.arqui.chatservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalanceResponseDTO {
    double totalSpent;
    Integer tripCount;
    double distance;
}
