package com.arqui.chatservice.service;

import com.arqui.chatservice.configuration.ToolRegistry;
import com.arqui.chatservice.dto.request.ChatRequestDTO;
import com.arqui.chatservice.dto.response.ChatResponseDTO;
import com.arqui.chatservice.dto.response.EstimatedTravelResponseDTO;
import com.arqui.chatservice.dto.response.GroqResult;
import com.arqui.chatservice.feignClients.GroqClient;
import com.arqui.chatservice.feignClients.TravelClient;
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
    private TravelClient travelClient;

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

        if (result.isToolCall()) {
            if (result.getToolName().equals("get_travel_price")) {

                // 1. Obtener argumentos que Groq pidió para ejecutar la tool
                Map<String, Object> args = result.getToolArguments();
                Long fromStationId = Long.valueOf(args.get("fromStationId").toString());
                Long toStationId = Long.valueOf(args.get("toStationId").toString());

                // 2. Llamar a tu microservicio real
                EstimatedTravelResponseDTO calc = travelClient.getEstimatedTravelPrice(fromStationId, toStationId);

                // 3. Construir el mensaje de TOOL RESULT
                Map<String, Object> toolMessage = Map.of(
                        "role", "tool",
                        "tool_call_id", result.getToolCallId(),
                        "content", "{ \"price\": " + calc.getPrice() + " }"
                );

                // 4. Agregar este mensaje a la conversación existente
                List<Object> followUpMessages = new ArrayList<>(messages);
                followUpMessages.add(toolMessage);

                // 5. Nueva request al modelo para que genere la respuesta final
                Map<String, Object> followUpRequest = new HashMap<>();
                followUpRequest.put("model", "llama-3.3-70b-versatile");
                followUpRequest.put("messages", followUpMessages);

                JsonNode json2 = groqClient.createChatCompletion(followUpRequest);
                GroqResult finalResult = GroqResult.fromJson(json2.toString());

                // 6. AHORA SÍ devolver respuesta al usuario
                return new ChatResponseDTO(finalResult.getContent());
            }
        }

        return new ChatResponseDTO(result.getContent());
    }
}
