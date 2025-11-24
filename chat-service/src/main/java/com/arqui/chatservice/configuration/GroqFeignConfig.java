package com.arqui.chatservice.configuration;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GroqFeignConfig {

    // Este bean sirve para interceptar las peticiones a Groq y agregarles los encabezados para autenticarse.
    @Bean
    public RequestInterceptor groqAuthInterceptor() {
        return template -> template.header(
                "Authorization",
                "Bearer " + ""
        );
    }
}
