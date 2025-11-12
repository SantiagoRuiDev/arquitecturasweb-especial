package com.arqui.travelservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.arqui.travelservice.dto.response.ScooterResponseDTO;

@FeignClient(name="scooter-service")
public interface ScooterClient {

    // Hace falta coordinar el metodo para obtener scooter por id
    @GetMapping("/{id}")
    ScooterResponseDTO getScooterById(@PathVariable Long id);

    
}
