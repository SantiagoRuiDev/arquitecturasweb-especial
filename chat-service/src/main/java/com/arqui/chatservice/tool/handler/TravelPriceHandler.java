package com.arqui.chatservice.tool.handler;

import com.arqui.chatservice.dto.response.EstimatedTravelResponseDTO;
import com.arqui.chatservice.feignClients.TravelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TravelPriceHandler implements ToolHandler {

    @Autowired
    private TravelClient travelClient;

    public boolean supports(String toolName) {
        return "get_travel_estimate_price".equals(toolName);
    }

    public Map<String, Object> execute(Map<String, Object> args) {
        Long fromStationId = Long.valueOf(args.get("fromStationId").toString());
        Long toStationId = Long.valueOf(args.get("toStationId").toString());
        EstimatedTravelResponseDTO calc = travelClient.getEstimatedTravelPrice(fromStationId, toStationId);

        Map<String, Object> toolMessage = Map.of(
                "content", "{ \"price\": " + calc.getPrice() + " }"
        );
        return  toolMessage;
    }
}
