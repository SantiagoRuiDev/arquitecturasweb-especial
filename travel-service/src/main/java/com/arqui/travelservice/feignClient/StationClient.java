package com.arqui.travelservice.feignClient;

import com.arqui.travelservice.dto.response.StationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "station-service")
public interface StationClient {

    @GetMapping("/api/stations/{id}")
    StationResponseDTO getStationById(@PathVariable Long id);
}
