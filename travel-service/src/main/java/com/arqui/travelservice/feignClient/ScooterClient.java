package com.arqui.travelservice.feignClient;

import com.arqui.travelservice.dto.request.ScooterRequestDTO;
import com.arqui.travelservice.dto.request.ScooterStatusRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.arqui.travelservice.dto.response.ScooterResponseDTO;
import com.arqui.travelservice.dto.request.ScooterUsageUpdateDTO;

@FeignClient(name="scooter-service")
public interface ScooterClient {

    // Hace falta coordinar el metodo para obtener scooter por id
    @GetMapping("/api/skateboards/{id}")
    ScooterResponseDTO getScooterById(@PathVariable Long id);

    // Actualizar el scooter
    @PutMapping("/api/skateboards/{id}")
    ScooterResponseDTO update(@PathVariable Long id, @RequestBody ScooterRequestDTO req);

    // Actualizar el estado del scooter
    @PutMapping("/api/skateboards/{id}/status")
    ScooterResponseDTO update(@PathVariable Long id, @RequestBody ScooterStatusRequestDTO req);

    // Actualizar kilometraje y pausas del scooter
    @PutMapping("/api/skateboards/{id}/usage")
    ScooterResponseDTO updateScooterUsage(@PathVariable Long id, @RequestBody ScooterUsageUpdateDTO usageUpdate);

}
