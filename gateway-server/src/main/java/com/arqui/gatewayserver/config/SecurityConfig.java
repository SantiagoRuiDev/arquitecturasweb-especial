package com.arqui.gatewayserver.config;

import com.arqui.gatewayserver.security.jwt.JwtFilter;
import com.arqui.gatewayserver.security.jwt.TokenProvider;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    public SecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        http.securityContextRepository(NoOpServerSecurityContextRepository.getInstance());

        // Esta configuración deshabilita el popup que pide datos de ingreso. (Ideal para poder ver el Swagger)
        http.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);
        http.formLogin(ServerHttpSecurity.FormLoginSpec::disable);

        http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/v3/api-docs/**",
                                "/account-service/**",
                                "/travel-service/**",
                                "/auth-service/**"
                        ).permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/authenticate/sign-in").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/authenticate/sign-up").permitAll()
                        .pathMatchers("/api/accounts/**").hasAuthority("RIDER")
                        .pathMatchers("/api/skateboards/**").hasAuthority("RIDER")
                        .pathMatchers("/api/payments/**").hasAuthority("RIDER")
                        .pathMatchers("/api/travels/**").hasAuthority("RIDER")
                        .pathMatchers("/api/chat/**").hasAuthority("RIDER")
                        .anyExchange().authenticated()
                );

        // si tienes un JWT filter, debe adaptarse a WebFlux
        http.addFilterAt(new JwtFilter(tokenProvider), SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }
}
