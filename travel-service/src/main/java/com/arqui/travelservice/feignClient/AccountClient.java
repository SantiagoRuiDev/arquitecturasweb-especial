package com.arqui.travelservice.feignClient;

import com.arqui.travelservice.dto.response.UserResponseDTO;
import com.arqui.travelservice.dto.request.DiscountRequestDTO;
import com.arqui.travelservice.dto.response.DiscountResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "account-service", url = "http://localhost:8080/api/accounts")
public interface AccountClient {

    @GetMapping("/users/{id}")
    UserResponseDTO getUserById(@PathVariable Long id);

    @PostMapping("/{id}/discount")
    DiscountResultDTO discount(@PathVariable Long id, @RequestBody DiscountRequestDTO req);
}
