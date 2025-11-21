package com.arqui.gatewayserver.config;

import com.arqui.gatewayserver.security.jwt.JwtFilter;
import com.arqui.gatewayserver.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    public SecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        http.securityContextRepository(NoOpServerSecurityContextRepository.getInstance());

        http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
                        .pathMatchers("/api/accounts/**").hasAuthority("RIDER")
                        .pathMatchers("/api/skateboards/**").hasAuthority("RIDER")
                        .pathMatchers("/api/payments/**").hasAuthority("RIDER")
                        .pathMatchers("/api/travels/**").hasAuthority("RIDER")
                        .anyExchange().authenticated()
                );

        // si tienes un JWT filter, debe adaptarse a WebFlux
        http.addFilterAt(new JwtFilter(tokenProvider), SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }
}
