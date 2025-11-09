package com.arqui.travelservice.feingClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.arqui.travelservice.dto.response.ScooterResponseDTO;

@FeignClient(name = "scooter-service", url = "http://localhost:8081")
public interface ScooterClient {

    // Hace falta coordinar el metodo para obtener scooter por id
    @GetMapping("/scooters/{id}")
    ScooterResponseDTO getScooterById(@PathVariable Long id);

    
}
