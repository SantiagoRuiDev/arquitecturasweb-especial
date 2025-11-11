package com.station.controller;

import com.station.dto.StationRequestDTO;
import com.station.dto.StationResponseDTO;
import com.station.dto.StationRequestDTO;
import com.station.dto.StationResponseDTO;
import com.station.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stations")
public class StationController {

    @Autowired
    private StationService stationService;

    @PostMapping
    public ResponseEntity<StationResponseDTO> save(@Valid @RequestBody StationRequestDTO dto) {
        return ResponseEntity.created(URI.create("/stations/" + saved.getId())).body(saved); }

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
