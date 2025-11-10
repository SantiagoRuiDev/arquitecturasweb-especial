package service;

import dto.SkateboardRequestDTO;
import dto.SkateboardResponseDTO;
import entity.Skateboard;
import entity.SkateboardStatus;
import entity.Station;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.SkateboardRepository;
import repository.StationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkateboardService {

    @Autowired
    private SkateboardRepository skateboardRepository;
    @Autowired
    private StationRepository stationRepository;

    public SkateboardResponseDTO save(SkateboardRequestDTO req) {
        Skateboard s = new Skateboard();
        s.setQrCode(req.getQrCode());
        s.setTotalKm(req.getTotalKm());
        s.setUsedTime(req.getUsedTime());
        s.setAvailable(req.isAvailable());
        s.setInMaintenance(req.isInMaintenance());
        s.setLatitude(req.getLatitude());
        s.setLenght(req.getLenght());
        s.setLastUpdate(req.getLastUpdate());
        s.setStatus(req.isInMaintenance() ? SkateboardStatus.IN_MAINTENANCE : SkateboardStatus.STOPED);

        Skateboard saved = skateboardRepository.save(s);
        return toResponseDTO(saved);
    }

    public SkateboardResponseDTO findById(Long id) {
        Skateboard s = skateboardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Skateboard not found"));
        return toResponseDTO(s);
    }

    public List<SkateboardResponseDTO> findAll() {
        return skateboardRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
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
        return toResponseDTO(skateboard);
    }


    public SkateboardResponseDTO assignToStation(Long skateboardId, Long stationId) {
        Skateboard skateboard = skateboardRepository.findById(skateboardId)
                .orElseThrow(() -> new EntityNotFoundException("Skateboard not found"));
        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new EntityNotFoundException("Station not found"));
        skateboard.setStation(station);
        skateboard.setStatus(SkateboardStatus.AT_STATION);
        skateboard.setAvailable(true);
        skateboardRepository.save(skateboard);
        return toResponseDTO(skateboard);
    }

    // Find scooters near a location
    public List<SkateboardResponseDTO> findNearby(double lat, double lon, double radiusKm) {
        return skateboardRepository.findAll().stream()
                .filter(s -> distanceKm(lat, lon, s.getLatitude(), s.getLenght()) <= radiusKm)
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<SkateboardResponseDTO> findScootersWithMoreThanKm(double minKm) {
        return skateboardRepository.findByTotalKm(minKm)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }


    public List<SkateboardResponseDTO> getScootersNeedingMaintenance() {
        return skateboardRepository.findAll().stream()
                .filter(s -> s.getTotalKm() > 1000 || s.getUsedTime() > 500)
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private double distanceKm(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Earth radius km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }


    private SkateboardResponseDTO toResponseDTO(Skateboard s) {
        return new SkateboardResponseDTO(
                s.getId(),
                s.getQrCode(),
                s.getTotalKm(),
                s.getUsedTime(),
                s.isAvailable(),
                s.isInMaintenance(),
                s.getCurrentStation() != null ? s.getCurrentStation().getName() : null,
                s.getLatitude(),
                s.getLenght(),
                s.getStatus().name(),
                s.getLastUpdate()
        );
    }


}