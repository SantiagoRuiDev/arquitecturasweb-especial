package com.arqui.chatservice.configuration;

import com.arqui.chatservice.tool.ChatTool;
import com.arqui.chatservice.tool.TravelPriceTool;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
// Esta clase es inyectable y funciono como un logger con las herramientas.
// que pueden ayudarle a Groq a definir respuestas que necesiten datos ya registrados en otros servicios
public class ToolRegistry {
    private final ObjectMapper mapper = new ObjectMapper();

    private final List<ChatTool> tools = List.of(
            new TravelPriceTool()
    );

    // Devuelve el JSON que necesita la petición HTTP que enviamos a Groq.
    public ArrayNode getToolsAsJson() {
        ArrayNode array = mapper.createArrayNode();

        for (ChatTool tool : tools) {
            ObjectNode toolNode = mapper.createObjectNode();
            ObjectNode functionNode = mapper.createObjectNode();

            functionNode.put("name", tool.getName());
            functionNode.put("description", tool.getDescription());
            functionNode.set("parameters", tool.getJsonSchema());

            toolNode.put("type", "function");
            toolNode.set("function", functionNode);

            array.add(toolNode);
        }

        return array;
    }
}