package com.arqui.authservice.feignClient;

import com.arqui.authservice.dto.response.AccountResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "account-service")
public interface AccountClient {
    @GetMapping("/api/accounts/search-by-auth-method/{id}")
    AccountResponseDTO getAccountByAuthMethod(@PathVariable Long id);
}
