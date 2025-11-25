package com.arqui.chatservice.service;

import com.arqui.chatservice.configuration.ToolRegistry;
import com.arqui.chatservice.dto.request.ChatRequestDTO;
import com.arqui.chatservice.dto.response.ChatResponseDTO;
import com.arqui.chatservice.dto.response.EstimatedTravelResponseDTO;
import com.arqui.chatservice.dto.response.GroqResult;
import com.arqui.chatservice.feignClients.GroqClient;
import com.arqui.chatservice.feignClients.TravelClient;
import com.arqui.chatservice.tool.GroqToolExecutor;
import com.arqui.chatservice.tool.handler.ToolHandler;
import com.arqui.chatservice.tool.handler.ToolHandlerRegistry;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    @Autowired
    private GroqClient groqClient;
    @Autowired
    private ToolRegistry toolRegistry;
    @Autowired
    private ToolHandlerRegistry toolHandlerRegistry;
    @Autowired
    private GroqToolExecutor groqToolExecutor;

    public ChatResponseDTO handleRequest (ChatRequestDTO req, Long account_id) {
        Map<String, Object> request = new HashMap<>();
        List<Object> messages = List.of(
                Map.of(
                        "role", "system",
                        "content", "Eres un asistente conectado a herramientas internas para dar respuestas reales."
                ),
                Map.of(
                        "role", "user",
                        "content", req.getMessage()
                )
        );
        request.put("model", "llama-3.3-70b-versatile");
        request.put("messages", messages);
        request.put("tools", toolRegistry.getToolsAsJson());
        request.put("tool_choice", "auto");

        JsonNode json = groqClient.createChatCompletion(request);
        GroqResult result = GroqResult.fromJson(json.toString());

        if (!result.isToolCall()) {
            return new ChatResponseDTO(result.getContent());
        }

        ToolHandler handler = toolHandlerRegistry.getHandler(result.getToolName());
        Map<String, Object> argsMap = result.getToolArguments();
        // Inyectar accountId
        argsMap.put("accountId", account_id);
        // Actualizar el JSON de toolArguments dentro de GroqResult
        result.setToolArguments(argsMap);
        // Ejecutar la herramienta
        Map<String, Object> toolResult = handler.execute(argsMap);
        argsMap.remove("accountId");

        return groqToolExecutor.resolveWithTool(result, messages, toolResult);
    }
}
