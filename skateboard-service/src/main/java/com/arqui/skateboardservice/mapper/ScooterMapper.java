package com.arqui.skateboardservice.mapper;

import com.arqui.skateboardservice.dto.SkateboardRequestDTO;
import com.arqui.skateboardservice.dto.SkateboardResponseDTO;
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
                entity.isAvailable(),
                entity.isInMaintenance(),
                entity.getCurrentStation() != null ? entity.getCurrentStation().getName() : null,
                entity.getLatitude(),
                entity.getLenght(),
                entity.getStatus().name(),
                entity.getLastUpdate()
        );
    }
}
