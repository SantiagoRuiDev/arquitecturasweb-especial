package com.arqui.chatservice.dto.response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Map;

@Getter
@Setter
public class GroqResult {

    private boolean toolCall;
    private String toolCallId;   // ID de la llamada a la herramienta
    private String toolName;
    private String toolArguments; // JSON string
    private String content;

    // Devuelve los argumentos como Map
    public Map<String, Object> getToolArguments() {
        if (toolArguments == null || toolArguments.isEmpty()) {
            return Collections.emptyMap();
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(toolArguments, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error parsing tool arguments", e);
        }
    }

    // Factory method para construir desde JSON crudo
    public static GroqResult fromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            JsonNode choices = root.path("choices");
            if (choices.isMissingNode() || !choices.isArray() || choices.size() == 0) {
                throw new RuntimeException("No hay respuestas validas.");
            }

            JsonNode choice = choices.get(0).path("message");
            GroqResult result = new GroqResult();

            JsonNode toolCalls = choice.path("tool_calls");
            if (!toolCalls.isMissingNode() && toolCalls.isArray() && toolCalls.size() > 0) {
                JsonNode toolCallNode = toolCalls.get(0);
                JsonNode functionNode = toolCallNode.path("function");

                result.toolCall = true;
                result.toolCallId = toolCallNode.path("id").asText(null); // null si no existe
                result.toolName = functionNode.path("name").asText(null);
                result.toolArguments = functionNode.path("arguments").asText(null);
            } else {
                result.toolCall = false;
                result.content = choice.path("content").asText(null);
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Error al intentar convertir la respuesta de Groq", e);
        }
    }
}
