package com.arqui.gatewayserver.controller;

import com.arqui.gatewayserver.dto.request.LoginRequestDTO;
import com.arqui.gatewayserver.dto.response.LoginResponseDTO;
import com.arqui.gatewayserver.webclient.AuthClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/authenticate")
@RequiredArgsConstructor
public class JwtController {
    @Autowired
    private final AuthClient authClient;

    @PostMapping
    public ResponseEntity<JWTToken> authenticate(@RequestBody LoginRequestDTO request) {
        Mono<LoginResponseDTO> loginResponse = authClient.authenticate(request);

        String jwt = loginResponse.toString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);

        return new ResponseEntity<>(new JWTToken(jwt), headers, HttpStatus.OK);
    }

    static class JWTToken {
        private String idToken;

        public JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        public String getIdToken() {
            return idToken;
        }

        public void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
