package com.arqui.chatservice.tool;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

@Component
public class TravelStatisticTool implements ChatTool {
    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public String getName() {
        return "get_account_travel_balance";
    }
    @Override
    public String getDescription() {
        return "Obtiene la cantidad de viajes y creditos gastados por una cuenta en un rango de tiempo";
    }
    @Override
    public JsonNode getJsonSchema() {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode root = mapper.createObjectNode();
        ObjectNode properties = mapper.createObjectNode();

        ObjectNode fromDate = mapper.createObjectNode();
        fromDate.put("type", "string");
        fromDate.put("format", "date");  // o "date-time" si incluye hora
        properties.set("fromDate", fromDate);

        ObjectNode toDate = mapper.createObjectNode();
        toDate.put("type", "string");
        toDate.put("format", "date");
        properties.set("toDate", toDate);

        ArrayNode required = mapper.createArrayNode();
        required.add("fromDate");
        required.add("toDate");

        root.put("type", "object");
        root.set("properties", properties);
        root.set("required", required);

        return root;
    }
}
