package com.arqui.accountservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    private Long accountId;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private Integer phone;
}
