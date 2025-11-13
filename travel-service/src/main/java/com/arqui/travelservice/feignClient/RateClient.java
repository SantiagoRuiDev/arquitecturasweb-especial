package com.arqui.travelservice.feignClient;
import com.arqui.travelservice.dto.response.RateResponseDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "rate-service")
public interface RateClient {

    @GetMapping("/api/rates")
    RateResponseDTO fetchActualRate();

}
