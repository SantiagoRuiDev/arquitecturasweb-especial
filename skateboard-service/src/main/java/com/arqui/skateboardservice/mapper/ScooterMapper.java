package com.arqui.skateboardservice.mapper;

import com.arqui.skateboardservice.dto.request.SkateboardRequestDTO;
import com.arqui.skateboardservice.dto.response.SkateboardResponseDTO;
import com.arqui.skateboardservice.entity.Skateboard;
import org.springframework.stereotype.Component;

@Component
public class ScooterMapper {
    public static Skateboard convertFromDTO(SkateboardRequestDTO dto){
        return new Skateboard();
    }

    public SkateboardResponseDTO convertFromEntity(Skateboard entity){
        return new SkateboardResponseDTO(
                entity.getId(),
                entity.getQrCode(),
                entity.getTotalKm(),
                entity.getUsedTime(),
                entity.getPausedTime(),
                entity.isAvailable(),
                entity.isInMaintenance(),
                entity.getStationId(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getStatus().name(),
                entity.getLastUpdate()
        );
    }
}
