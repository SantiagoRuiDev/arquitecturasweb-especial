package com.arqui.authservice.dto.request;
import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username;
    private String password;
}
