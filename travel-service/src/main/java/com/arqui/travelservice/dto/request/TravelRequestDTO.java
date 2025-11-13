package com.arqui.travelservice.dto.request;

import java.time.LocalDateTime;

// DTO para la solicitud de inicio de viaje
import com.arqui.travelservice.entity.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelRequestDTO {
    private Long accountId;           // Cuenta asociada (básica o premium)
    private Long userId;              // Usuario que inicia el viaje
    private Long scooterId;           // Monopatín escaneado por qr
    private Long startStopId;         // Parada donde se inicia el viaje (Hay q ver si se cambia)
    private LocalDateTime startTime;
    private AccountType userType;          // Tipo de usuario
}