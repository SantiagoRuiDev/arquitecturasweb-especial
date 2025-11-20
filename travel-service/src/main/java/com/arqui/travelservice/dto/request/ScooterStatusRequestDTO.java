package com.arqui.travelservice.dto.request;

import com.arqui.travelservice.entity.SkateboardStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScooterStatusRequestDTO {
    SkateboardStatus status;
}
