package com.arqui.authservice.controller;

import com.arqui.authservice.dto.request.LoginRequestDTO;
import com.arqui.authservice.dto.response.LoginResponseDTO;
import com.arqui.authservice.dto.response.RegisterResponseDTO;
import com.arqui.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO req) {

        String token = authService.authenticate(req.getUsername(), req.getPassword());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody LoginRequestDTO req) {

        String res = authService.register(req.getUsername(), req.getPassword());

        return ResponseEntity.ok(new RegisterResponseDTO(res));
    }
}
