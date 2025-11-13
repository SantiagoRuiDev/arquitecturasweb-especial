package com.arqui.travelservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PauseRequestDTO {
    private Long travelId;
    private LocalDateTime startPause;
}
