package com.arqui.travelservice.feignClient;

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

    // Actualizar kilometraje y pausas del scooter
    @PutMapping("/api/skateboards/{id}/usage")
    ScooterResponseDTO updateScooterUsage(@PathVariable Long id, @RequestBody ScooterUsageUpdateDTO usageUpdate);

}
