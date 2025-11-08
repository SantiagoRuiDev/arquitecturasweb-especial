package com.arqui.travelservice.service;

import com.arqui.travelservice.dto.request.TravelRequestDTO;
import com.arqui.travelservice.dto.request.TravelEndRequestDTO;
import com.arqui.travelservice.dto.response.TravelResponseDTO;
import com.arqui.travelservice.dto.TravelSummaryDTO;

import java.util.List;

public interface TravelService {
    TravelResponseDTO startTravel(TravelRequestDTO request);
    TravelResponseDTO endTravel(TravelEndRequestDTO request);
    TravelResponseDTO getTravelById(Long id);
    List<TravelSummaryDTO> getAllTravels();
}
