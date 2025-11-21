package com.arqui.gatewayserver.webclient;

import com.arqui.gatewayserver.dto.request.LoginRequestDTO;
import com.arqui.gatewayserver.dto.response.LoginResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthClient {

    private final WebClient webClient;

    public AuthClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://auth-service").build();
    }

    public Mono<LoginResponseDTO> authenticate(LoginRequestDTO req) {
        return webClient.post()
                .uri("/api/auth/authenticate")
                .bodyValue(req)
                .retrieve()
                .bodyToMono(LoginResponseDTO.class);
    }
}