package com.arqui.travelservice.controller;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import com.arqui.travelservice.service.TravelService;
import com.arqui.travelservice.dto.request.TravelRequestDTO;
import com.arqui.travelservice.dto.request.PauseEndRequestDTO;
import com.arqui.travelservice.dto.request.PauseRequestDTO;
import com.arqui.travelservice.dto.request.TravelEndRequestDTO;
import com.arqui.travelservice.dto.response.TravelResponseDTO;
import com.arqui.travelservice.dto.ScooterUsageDTO;
import com.arqui.travelservice.dto.TravelReportDTO;

@RestController
@RequestMapping("/api/travels")
public class TravelController {

    private final TravelService travelService;

    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    // C - Consultar los monopatines con mas de X viajes en un cierto año
    @GetMapping("/scooters/top/{year}/{minTrips}")
    public List<ScooterUsageDTO> getTopScooters(@PathVariable int year, @PathVariable int minTrips) {
        return travelService.getTopScooters(year, minTrips);
    }

    // E - Ver los usuarios que más utilizan los monopatines, filtrado por período y por tipo de usuario
    @GetMapping("/users/{startDate}/{endDate}/{userType}")
    public List<TravelReportDTO> getUserTripsByPeriodAndType(@PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate, @PathVariable Long userType) {
        return travelService.getUserTripsByPeriodAndType(startDate, endDate, userType);
    }

    /* -------------------------------------------------------------------------------------------------------- */

    // Empezar un viaje
    @PostMapping("/start")
    public TravelResponseDTO startTravel(@RequestBody TravelRequestDTO dto) {
        return travelService.startTravel(dto);
    }

    // Terminar un viaje 
    @PostMapping("/end")
    public TravelResponseDTO endTravel(@RequestBody TravelEndRequestDTO dto) {
        return travelService.endTravel(dto);
    }

    // Pausar un viaje
    @PostMapping("/pause")
    public TravelResponseDTO pauseTravel(@RequestBody PauseRequestDTO dto) {
        return travelService.pauseTravel(dto);
    }

    // Reanudar un viaje en pausa
    @PostMapping("/resume")
    public TravelResponseDTO resumePause(@RequestBody PauseEndRequestDTO dto) {
        return travelService.resumePause(dto);
    }

    // Get travel by ID
    @GetMapping("/{id}")
    public TravelResponseDTO getById(@PathVariable Long id) {
        return travelService.getTravelById(id);
    }

    // Get all travels 
    @GetMapping
    public List<TravelResponseDTO> getAll() {
        return travelService.getAllTravels();
    }
}
