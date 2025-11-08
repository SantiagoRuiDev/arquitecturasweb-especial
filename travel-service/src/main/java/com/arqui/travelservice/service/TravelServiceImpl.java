package com.arqui.travelservice.service;

import com.arqui.travelservice.dto.request.TravelRequestDTO;
import com.arqui.travelservice.dto.request.TravelEndRequestDTO;
import com.arqui.travelservice.dto.response.TravelResponseDTO;
import com.arqui.travelservice.mapper.TravelMapper;
import com.arqui.travelservice.dto.TravelSummaryDTO;
import com.arqui.travelservice.domain.model.Travel;
import com.arqui.travelservice.domain.model.TravelStatus;
import com.arqui.travelservice.domain.repository.TravelRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TravelServiceImpl implements TravelService {

    private final TravelRepository travelRepository;

    public TravelServiceImpl(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    @Override
    public TravelResponseDTO startTravel(TravelRequestDTO request) {
        Travel travel = TravelMapper.fromRequestDTO(request);
        return TravelMapper.toDTO(travelRepository.save(travel));
    }

    @Override
    public TravelResponseDTO endTravel(TravelEndRequestDTO request) {
        Travel travel = travelRepository.findById(request.getTravelId())
                .orElseThrow(() -> new RuntimeException("Travel not found"));

        travel.setEndStopId(request.getEndStopId());
        travel.setEndTime(request.getEndTime());
        travel.setDistanceKm(request.getDistanceKm());
        travel.setStatus(TravelStatus.FINISHED);

        // Lógica de cálculo de costo (simplificada)
        travel.setCost(request.getDistanceKm() * 10.0);

        return TravelMapper.toDTO(travelRepository.save(travel));
    }

    // Obtener un viaje por su ID
    @Override
    public TravelResponseDTO getTravelById(Long id) {
        return travelRepository.findById(id)
                .map(TravelMapper::toDTO) // Mapear a DTO
                .orElseThrow(() -> new RuntimeException("La id proporcionada no corresponde a ningún viaje existente"));
    }

    @Override
    public List<TravelSummaryDTO> getAllTravels() {
        return travelRepository.findAll().stream().map(t -> {
            TravelSummaryDTO dto = new TravelSummaryDTO();
            dto.setId(t.getId());
            dto.setStartTime(t.getStartTime());
            dto.setEndTime(t.getEndTime());
            dto.setDistanceKm(t.getDistanceKm());
            dto.setCost(t.getCost());
            return dto;
        }).toList();
    }
}
