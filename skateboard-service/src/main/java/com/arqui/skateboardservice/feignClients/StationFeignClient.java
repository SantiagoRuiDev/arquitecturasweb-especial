package com.arqui.skateboardservice.feignClients;


import com.arqui.skateboardservice.dto.response.StationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name="station", url="http://localhost:8003/stations")
public interface StationFeignClient {
    @GetMapping("/{id}")
    StationResponseDTO findById(@PathVariable Long id);
}
