package com.arqui.travelservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.arqui.travelservice.dto.response.UserResponseDTO;

@FeignClient(name="account-service", url="http://localhost:8080/account")
public interface UserClient{

    // Hace falta coordinar el metodo para obtener usuario por id
    @GetMapping("/users/{id}")
    UserResponseDTO getUserById(@PathVariable Long id);
    
}
