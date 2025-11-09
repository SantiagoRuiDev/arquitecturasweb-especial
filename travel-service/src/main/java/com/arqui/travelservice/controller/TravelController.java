package com.arqui.travelservice.controller;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
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

    // A - reporte de uso por KM / pausas
    @GetMapping("/report/{id}")
    public TravelReportDTO getTravelReport(@PathVariable Long id) {
        return travelService.getTravelReport(id);
    }

    // C - Consultar los monopatines con mas de X viajes en un cierto año
    @GetMapping("/scooters/top/{year}/{minTrips}")
    public List<ScooterUsageDTO> getTopScooters(@PathVariable int year, @PathVariable int minTrips) {
        return travelService.getTopScooters(year, minTrips);
    }

    // E - Consultar los viajes de un usuario en un cierto año
    @GetMapping("/users/{userId}/trips/{year}")
    public List<TravelReportDTO> getUserTripsByYear(@PathVariable Long userId, @PathVariable int year) {
        return travelService.getUserTripsByYear(userId, year);
    }

    // D - Consultar los viajes en un cierto rango de tiempo
    @GetMapping("/trips")
    public List<TravelReportDTO> getTripsByDateRange(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return travelService.getTripsByDateRange(startDate, endDate);
    }

    /* -------------------------------------------------------------------------------------------------------- */

    // Start a new travel
    @PostMapping("/start")
    public TravelResponseDTO startTravel(@RequestBody TravelRequestDTO dto) {
        return travelService.startTravel(dto);
    }

    // End an existing travel
    @PostMapping("/end")
    public TravelResponseDTO endTravel(@RequestBody TravelEndRequestDTO dto) {
        return travelService.endTravel(dto);
    }


    // Get travel by ID
    @GetMapping("/{id}")
    public TravelResponseDTO getById(@PathVariable Long id) {
        return travelService.getTravelById(id);
    }

    // Get all travels - resumen de estos - Entity = TravelSummaryDTO
    @GetMapping
    public List<TravelReportDTO> getAll() {
        return travelService.getAllTravels();
    }
}
