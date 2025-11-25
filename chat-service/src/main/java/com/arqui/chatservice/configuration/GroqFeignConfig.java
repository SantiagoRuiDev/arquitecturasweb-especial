package com.arqui.chatservice.configuration;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GroqFeignConfig {
    @Value("${app.groq_key}")
    private String groq_key;
    // Este bean sirve para interceptar las peticiones a Groq y agregarles los encabezados para autenticarse.
    @Bean
    public RequestInterceptor groqAuthInterceptor() {
        return template -> template.header(
                "Authorization",
                "Bearer " + groq_key // Poner la key aqui solo para testeo rapido.
        );
    }
}
