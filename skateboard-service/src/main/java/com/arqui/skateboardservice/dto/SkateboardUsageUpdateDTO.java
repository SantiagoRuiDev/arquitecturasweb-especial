package com.arqui.skateboardservice.dto;

import com.arqui.skateboardservice.dto.request.PauseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkateboardUsageUpdateDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double km;
    private List<PauseDTO> pauses;
}
