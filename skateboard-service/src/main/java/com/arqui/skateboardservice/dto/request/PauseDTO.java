package com.arqui.skateboardservice.dto.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PauseDTO {
    private String id;
    private LocalDateTime startPause;
    private LocalDateTime endPause;
    private boolean exceededTimeLimit;

}