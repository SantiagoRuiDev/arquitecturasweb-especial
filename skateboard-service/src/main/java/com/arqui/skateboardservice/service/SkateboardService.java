package com.arqui.skateboardservice.service;

import com.arqui.skateboardservice.dto.SkateboardUsageUpdateDTO;
import com.arqui.skateboardservice.dto.request.SkateboardRequestDTO;
import com.arqui.skateboardservice.dto.response.SkateboardResponseDTO;
import com.arqui.skateboardservice.dto.response.StationResponseDTO;
import com.arqui.skateboardservice.entity.Skateboard;
import com.arqui.skateboardservice.entity.SkateboardStatus;
import com.arqui.skateboardservice.feignClients.StationFeignClient;
import com.arqui.skateboardservice.mapper.ScooterMapper;
import com.arqui.skateboardservice.repository.SkateboardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.arqui.skateboardservice.utils.calculateDistanceKm.distanceKm;

@Service
public class SkateboardService {

    @Autowired
    private SkateboardRepository skateboardRepository;

    @Autowired
    private ScooterMapper scooterMapper;

    @Autowired
    private StationFeignClient stationClient;


    public SkateboardResponseDTO save(SkateboardRequestDTO req) {
        Skateboard s = new Skateboard();
        s.setQrCode(req.getQrCode());
        s.setTotalKm(req.getTotalKm());
        s.setUsedTime(req.getUsedTime());
        s.setAvailable(req.isAvailable());
        s.setInMaintenance(req.isInMaintenance());
        s.setLatitude(req.getLatitude());
        s.setLongitude(req.getLongitude());
        s.setLastUpdate(req.getLastUpdate());
        s.setStatus(req.isInMaintenance() ? SkateboardStatus.IN_MAINTENANCE : SkateboardStatus.STOPED);

        Skateboard saved = skateboardRepository.save(s);
        return scooterMapper.convertFromEntity(saved);
    }

    public SkateboardResponseDTO findById(Long id) {
        Skateboard s = skateboardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Skateboard not found"));
        return scooterMapper.convertFromEntity(s);
    }

    public List<SkateboardResponseDTO> findAll() {
        return skateboardRepository.findAll()
                .stream()
                .map(scooterMapper::convertFromEntity)
                .collect(Collectors.toList());
    }

    public boolean delete(Long id) {
        if(skateboardRepository.existsById(id)) {
            skateboardRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public SkateboardResponseDTO registerMaintenance(Long id) {
        Skateboard skateboard = skateboardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Skateboard not found"));
        skateboard.setInMaintenance(true);
        skateboard.setAvailable(false);
        skateboard.setStatus(SkateboardStatus.IN_MAINTENANCE);
        skateboardRepository.save(skateboard);
        return scooterMapper.convertFromEntity(skateboard);
    }


    public SkateboardResponseDTO assignToStation(Long skateboardId, Long stationId) {
        Skateboard skateboard = skateboardRepository.findById(skateboardId)
                .orElseThrow(() -> new EntityNotFoundException("Skateboard not found"));
        try{
            StationResponseDTO stationResponse  = stationClient.findById(stationId);
        }catch(EntityNotFoundException e){
               throw new EntityNotFoundException("Station not found");
        }
        skateboard.setStatus(SkateboardStatus.AT_STATION);
        skateboard.setAvailable(true);
        skateboardRepository.save(skateboard);
        return scooterMapper.convertFromEntity(skateboard);
    }

    // Find scooters near a location
    public List<SkateboardResponseDTO> findNearby(double lat, double lon, double radiusKm) {
        return skateboardRepository.findAll().stream()
                .filter(s -> distanceKm(lat, lon, s.getLatitude(), s.getLongitude()) <= radiusKm)
                .map(scooterMapper::convertFromEntity)
                .collect(Collectors.toList());
    }

    public List<SkateboardResponseDTO> findScootersWithMoreThanKm(double minKm) {
        return skateboardRepository.findByTotalKmGreaterThanEqual(minKm)
                .stream()
                .map(scooterMapper::convertFromEntity)
                .collect(Collectors.toList());
    }


    public List<SkateboardResponseDTO> getScootersNeedingMaintenance() {
        return skateboardRepository.findAll().stream()
                .filter(s -> s.getTotalKm() > 1000 || s.getUsedTime() > 500)
                .map(scooterMapper::convertFromEntity)
                .collect(Collectors.toList());
    }


    public SkateboardResponseDTO updateSkateboardUsage(Long id, SkateboardUsageUpdateDTO usageUpdate) {
        Skateboard skateboard = skateboardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Skateboard not found"));

        long totalMinutes = Duration.between(
                usageUpdate.getStartTime(), usageUpdate.getEndTime()
        ).toMinutes();

        double effectiveMinutes = totalMinutes;
        if (usageUpdate.getPauseMinutes() != null) {
            effectiveMinutes -= usageUpdate.getPauseMinutes();
        }

        double hoursUsed = effectiveMinutes / 60.0;

        if (skateboard.getUsedTime() == null)
            skateboard.setUsedTime(0.0);

        skateboard.setUsedTime(skateboard.getUsedTime() + hoursUsed);
        skateboard.setLastUpdate(LocalDateTime.now());
        skateboardRepository.save(skateboard);

        return toResponseDTO(skateboard);
    }

    private SkateboardResponseDTO toResponseDTO(Skateboard s) {
        return new SkateboardResponseDTO(
                s.getId(),
                s.getQrCode(),
                s.getTotalKm(),
                s.getUsedTime(),
                s.isAvailable(),
                s.isInMaintenance(),
                s.getStationId(),
                s.getLatitude(),
                s.getLongitude(),
                s.getStatus().name(),
                s.getLastUpdate()
        );
    }

}