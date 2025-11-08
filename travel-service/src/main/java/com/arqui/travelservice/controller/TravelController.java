package com.arqui.travelservice.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/travels")
public class TravelController {

    private final TravelService travelService;

    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

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

    // Get all travels - resumen de estos
    @GetMapping
    public List<TravelSummaryDTO> getAll() {
        return travelService.getAllTravels();
    }
}
