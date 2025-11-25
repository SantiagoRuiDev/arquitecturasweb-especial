package com.arqui.authservice.service;

import com.arqui.authservice.dto.response.AccountResponseDTO;
import com.arqui.authservice.entity.Credential;
import com.arqui.authservice.feignClient.AccountClient;
import com.arqui.authservice.repository.CredentialRepository;
import com.arqui.authservice.security.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    @Autowired
    private CredentialRepository credentialRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private AccountClient accountClient;

    public String authenticate(String username, String password) {
        Credential row = credentialRepository.findByUsername(username);
        if (row == null) {
            return null;
        }

        if (!passwordEncoder.matches(password, row.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        AccountResponseDTO relatedTo = null;
        try {
            relatedTo = accountClient.getAccountByAuthMethod(row.getId());
        } catch(Exception e) {
            throw new RuntimeException(e);
        }

        UserDetails userDetails = User.builder()
                .username(username)
                .password("") // no importa, no se usa
                .authorities(row.getRole()) // por ejemplo "ROLE_ADMIN"
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        return tokenProvider.createToken(authentication, relatedTo.getId());
    }


    public String register(String username, String password) {
        Credential row = credentialRepository.findByUsername(username);

        if (row != null) {
            throw new RuntimeException("Este usuario ya existe");
        }

        Credential newRow = new Credential();
        newRow.setUsername(username);
        newRow.setPassword(passwordEncoder.encode(password));
        newRow.setRole("RIDER");
        credentialRepository.save(newRow);

        return "Metodo de autenticación registrado correctamente, para obtener un token de acceso inicia sesión";
    }
}
