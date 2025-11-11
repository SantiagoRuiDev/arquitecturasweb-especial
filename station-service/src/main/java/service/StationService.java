package com.station.service;

import com.station.dto.StationRequestDTO;
import com.station.dto.StationResponseDTO;

import java.util.List;

public interface StationService {
    StationResponseDTO save(StationRequestDTO stationDTO);
    StationResponseDTO update(Long id, StationRequestDTO stationDTO);
    void delete(Long id);
    StationResponseDTO findById(Long id);
    List<StationResponseDTO> findAll();
    List<StationResponseDTO> getActiveStations();
}

