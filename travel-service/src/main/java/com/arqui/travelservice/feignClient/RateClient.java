package com.arqui.travelservice.feignClient;
import com.arqui.travelservice.dto.response.RateResponseDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "rate-service", url = "http://localhost:8080")
public interface RateClient {

    @GetMapping("/rates")
    RateResponseDTO fetchActualRate();

}
