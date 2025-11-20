package com.arqui.travelservice.feignClient;

import com.arqui.travelservice.dto.response.AccountResponseDTO;
import com.arqui.travelservice.dto.response.UserResponseDTO;
import com.arqui.travelservice.dto.request.DiscountRequestDTO;
import com.arqui.travelservice.dto.response.DiscountResultDTO;
import com.arqui.travelservice.entity.AccountType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "account-service")
public interface AccountClient {

    @GetMapping("/api/users/{id}")
    UserResponseDTO getUserById(@PathVariable Long id);

    @GetMapping("/api/accounts")
    List<AccountResponseDTO> findAllByType(@RequestParam("type") AccountType type);

    @PostMapping("/api/accounts/{id}/discount")
    DiscountResultDTO discount(@PathVariable Long id, @RequestBody DiscountRequestDTO req);
}
