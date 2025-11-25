package com.arqui.chatservice.tool;

import com.arqui.chatservice.dto.response.ChatResponseDTO;
import com.arqui.chatservice.dto.response.GroqResult;
import com.arqui.chatservice.feignClients.GroqClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GroqToolExecutor {

    @Autowired
    private GroqClient groqClient;

    @Autowired
    private ObjectMapper mapper;

    public ChatResponseDTO resolveWithTool(
            GroqResult result,
            List<Object> messages,
            Map<String, Object> toolResult
    ) {
        String jsonContent;
        try {
            jsonContent = mapper.writeValueAsString(toolResult);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializando toolResult", e);
        }

        Map<String, Object> toolMessage = Map.of(
                "role", "tool",
                "tool_call_id", result.getToolCallId(),
                "content", jsonContent
        );

        List<Object> followUpMessages = new ArrayList<>(messages);
        followUpMessages.add(toolMessage);

        Map<String, Object> followUpRequest = Map.of(
                "model", "llama-3.3-70b-versatile",
                "messages", List.of(
                        Map.of("role", "system", "content", "Eres un asistente que usa los resultados de herramientas para responder al usuario."),
                        toolMessage
                )
        );

        var json2 = groqClient.createChatCompletion(followUpRequest);
        GroqResult finalResult = GroqResult.fromJson(json2.toString());

        return new ChatResponseDTO(finalResult.getContent());
    }
}
