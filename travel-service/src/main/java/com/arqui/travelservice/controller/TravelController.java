package com.arqui.travelservice.controller;

import com.arqui.travelservice.dto.response.UserScooterUsageDTO;
import com.arqui.travelservice.entity.AccountType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.arqui.travelservice.service.TravelService;
import com.arqui.travelservice.dto.request.TravelRequestDTO;
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
    public List<TravelReportDTO> getUserTripsByPeriodAndType(@PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate, @PathVariable AccountType userType) {
        return travelService.getUserTripsByPeriodAndType(startDate, endDate, userType);
    }

    // H - Como usuario quiero saber cuánto he usado los monopatines en un período, y opcionalmente si otros usuarios relacionados a mi cuenta los han usado.
    @GetMapping("/usage/{userId}/{startDate}/{endDate}")
    public List<UserScooterUsageDTO> getScootersUsageByUser(@PathVariable Long userId, @PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate, @RequestParam(defaultValue = "false") boolean includeRelatedUsers) {
        return travelService.getScootersUsageByUser(userId, startDate, endDate, includeRelatedUsers);
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
    @PostMapping("/pause/{id}")
    public TravelResponseDTO pauseTravel(@PathVariable Long id) {
        return travelService.pauseTravel(id);
    }

    // Reanudar un viaje en pausa
    @PostMapping("/resume/{id}")
    public TravelResponseDTO resumePause(@PathVariable Long id) {
        return travelService.resumePause(id);
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
