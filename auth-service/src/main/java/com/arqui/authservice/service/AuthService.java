package com.arqui.authservice.service;

import com.arqui.authservice.entity.Credential;
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

    public String authenticate(String username, String password) {
        Credential row = credentialRepository.findByUsername(username);
        if (row == null) {
            return null;
        }

        if (!passwordEncoder.matches(password, row.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        UserDetails userDetails = User.builder()
                .username(username)
                .password("") // no importa, no se usa
                .authorities(row.getRole()) // por ejemplo "ROLE_ADMIN"
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        return tokenProvider.createToken(authentication);
    }


    public String register(String username, String password) {
        Credential row = credentialRepository.findByUsername(username);
        System.out.println("0");

        if (row != null) {
            throw new RuntimeException("Este usuario ya existe");
        }

        System.out.println("1");

        Credential newRow = new Credential();
        newRow.setUsername(username);
        newRow.setPassword(passwordEncoder.encode(password));
        newRow.setRole("RIDER");
        credentialRepository.save(newRow);

        UserDetails userDetails = User.builder()
                .username(username)
                .password("") // no importa, no se usa
                .authorities(newRow.getRole()) // por ejemplo "ROLE_ADMIN"
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        return tokenProvider.createToken(authentication);
    }
}
