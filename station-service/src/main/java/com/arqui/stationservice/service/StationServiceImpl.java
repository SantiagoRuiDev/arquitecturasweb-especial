package com.arqui.stationservice.service;

import com.arqui.stationservice.dto.request.StationRequestDTO;
import com.arqui.stationservice.dto.response.StationResponseDTO;
import com.arqui.stationservice.entity.Station;
import com.arqui.stationservice.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;

    @Autowired
    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public StationResponseDTO save(StationRequestDTO dto) {
        Station station = mapToEntity(dto);
        station.setActive(true);
        Station saved = stationRepository.save(station);
        return mapToResponse(saved);
    }

    @Override
    public StationResponseDTO update(Long id, StationRequestDTO dto) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Station not found with id: " + id));

        station.setName(dto.getName());
        station.setAddress(dto.getAddress());
        station.setLatitude(dto.getLatitude());
        station.setLongitude(dto.getLongitude());
        station.setCapacity(dto.getCapacity());

        Station updated = stationRepository.save(station);
        return mapToResponse(updated);
    }

    @Override
    public void delete(Long id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Station not found with id: " + id));
        // soft delete: mark inactive
        station.setActive(false);
        stationRepository.save(station);
    }

    @Override
    public StationResponseDTO findById(Long id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Station not found with id: " + id));
        return mapToResponse(station);
    }

    @Override
    public List<StationResponseDTO> findAll() {
        return stationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<StationResponseDTO> getActiveStations() {
        return stationRepository.findByActive(true)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Mapping helpers

    private StationResponseDTO mapToResponse(Station station) {
        StationResponseDTO dto = new StationResponseDTO();
        dto.setId(station.getId());
        dto.setName(station.getName());
        dto.setAddress(station.getAddress());
        dto.setLatitude(station.getLatitude());
        dto.setLongitude(station.getLongitude());
        dto.setCapacity(station.getCapacity());
        // If DTO has active field; if not, you can remove this line
        try {
            dto.getClass().getDeclaredField("active");
            // set active via reflection-safe approach
            // but prefer DTO to include active; here set by casting if field exists
        } catch (NoSuchFieldException e) {
            // ignore: DTO doesn't have active field
        }
        return dto;
    }

    private Station mapToEntity(StationRequestDTO dto) {
        Station s = new Station();
        s.setName(dto.getName());
        s.setAddress(dto.getAddress());
        s.setLatitude(dto.getLatitude());
        s.setLongitude(dto.getLongitude());
        s.setCapacity(dto.getCapacity());
        return s;
    }
}
