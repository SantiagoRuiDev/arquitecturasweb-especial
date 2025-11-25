package com.arqui.chatservice.feignClients;

import com.arqui.chatservice.dto.response.AccountBalanceResponseDTO;
import com.arqui.chatservice.dto.response.EstimatedTravelResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name="travel-service")
public interface TravelClient {
    @GetMapping("/api/travels/balance/{accountId}/{fromDate}/{toDate}")
    AccountBalanceResponseDTO getBalanceReportByAccount(@PathVariable String fromDate, @PathVariable String toDate, @PathVariable Long accountId);
    @GetMapping("/api/travels/estimate/{startStation}/{endStation}")
    EstimatedTravelResponseDTO getEstimatedTravelPrice(@PathVariable Long startStation, @PathVariable Long endStation);
}