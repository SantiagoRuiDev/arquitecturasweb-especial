package com.arqui.accountservice.dto.response;

import com.arqui.accountservice.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private List<Long> accounts;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private Integer phone;
}
