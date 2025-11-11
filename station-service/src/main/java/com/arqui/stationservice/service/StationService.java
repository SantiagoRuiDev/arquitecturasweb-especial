package com.arqui.stationservice.service;

import com.arqui.stationservice.dto.request.StationRequestDTO;
import com.arqui.stationservice.dto.response.StationResponseDTO;

import java.util.List;

public interface StationService {
    StationResponseDTO save(StationRequestDTO stationDTO);
    StationResponseDTO update(Long id, StationRequestDTO stationDTO);
    void delete(Long id);
    StationResponseDTO findById(Long id);
    List<StationResponseDTO> findAll();
    List<StationResponseDTO> getActiveStations();
}

