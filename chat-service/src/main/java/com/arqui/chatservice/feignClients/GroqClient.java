package com.arqui.chatservice.feignClients;

import com.arqui.chatservice.configuration.GroqFeignConfig;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(
        name = "groqClient",
        url = "https://api.groq.com/openai/v1",
        configuration = GroqFeignConfig.class // Inyectamos los encabezados.
)
public interface GroqClient {

    @PostMapping("/chat/completions")
    JsonNode createChatCompletion(@RequestBody Map<String, Object> request);
}