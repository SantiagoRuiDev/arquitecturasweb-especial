package com.arqui.stationservice.controller;

import com.arqui.stationservice.dto.request.StationRequestDTO;
import com.arqui.stationservice.dto.response.StationResponseDTO;
import com.arqui.stationservice.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stations")
public class StationController {

    @Autowired
    private StationService stationService;

    @PostMapping("")
    public ResponseEntity<StationResponseDTO> save(@RequestBody StationRequestDTO dto) {
        StationResponseDTO row = stationService.save(dto);
        return ResponseEntity.ok(row);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StationResponseDTO> update(
            @PathVariable Long id,
            @RequestBody StationRequestDTO dto) {
        StationResponseDTO updated = stationService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        stationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StationResponseDTO> findById(@PathVariable Long id) {
        StationResponseDTO station = stationService.findById(id);
        return ResponseEntity.ok(station);
    }

    @GetMapping
    public ResponseEntity<List<StationResponseDTO>> findAll() {
        List<StationResponseDTO> stations = stationService.findAll();
        return ResponseEntity.ok(stations);
    }

    @GetMapping("/active")
    public ResponseEntity<List<StationResponseDTO>> getActiveStations() {
        return ResponseEntity.ok(stationService.getActiveStations()); }
}
