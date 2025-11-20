package com.arqui.skateboardservice.dto.request;

import com.arqui.skateboardservice.entity.SkateboardStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScooterStatusRequestDTO {
    SkateboardStatus status;
}
