package com.arqui.travelservice.dto.response;

import com.arqui.travelservice.entity.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountResponseDTO {
    private Long id;
    private Date createdAt;
    private AccountType type;
    private Double credits;
}
