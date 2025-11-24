package com.arqui.chatservice.tool;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

@Component
public class TravelPriceTool implements ChatTool {
    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public String getName() {
        return "get_travel_price";
    }
    @Override
    public String getDescription() {
        return "Obtiene el precio real del viaje entre dos estaciones";
    }
    @Override
    public JsonNode getJsonSchema() {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode root = mapper.createObjectNode();
        ObjectNode properties = mapper.createObjectNode();

        // Definir propiedad fromStationId
        ObjectNode fromStation = mapper.createObjectNode();
        fromStation.put("type", "number");
        properties.set("fromStationId", fromStation);

        // Definir propiedad toStationId
        ObjectNode toStation = mapper.createObjectNode();
        toStation.put("type", "number");
        properties.set("toStationId", toStation);

        // Required fields array
        ArrayNode required = mapper.createArrayNode();
        required.add("fromStationId");
        required.add("toStationId");

        // Construir JSON final
        root.put("type", "object");
        root.set("properties", properties);
        root.set("required", required);

        return root;
    }
}
