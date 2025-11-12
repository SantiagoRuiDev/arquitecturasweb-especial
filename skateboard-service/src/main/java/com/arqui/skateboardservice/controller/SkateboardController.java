package com.arqui.skateboardservice.controller;

import com.arqui.skateboardservice.dto.request.SkateboardRequestDTO;
import com.arqui.skateboardservice.dto.response.SkateboardResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arqui.skateboardservice.service.SkateboardService;

import java.util.List;

@RestController
@RequestMapping("/api/skateboards")
public class SkateboardController {

    @Autowired
    private SkateboardService skateboardService;

    // (3) - Agregar monopatin
    @PostMapping("")
    public ResponseEntity<SkateboardResponseDTO> save(@RequestBody SkateboardRequestDTO req) {
        SkateboardResponseDTO res =skateboardService.save(req);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkateboardResponseDTO> findById(@PathVariable Long id) {
        SkateboardResponseDTO res = skateboardService.findById(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping("")
    public ResponseEntity<List<SkateboardResponseDTO>> findAll() {
        List<SkateboardResponseDTO> res = skateboardService.findAll();
        return ResponseEntity.ok(res);
    }

    // (3) - Quitar monopatin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean res = skateboardService.delete(id);
        return res ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // (3) - Registrar monopatín en mantenimiento
    @PutMapping("/{id}/maintenance")
    public ResponseEntity< SkateboardResponseDTO> registerMaintenance(@PathVariable Long id) {
       SkateboardResponseDTO res = skateboardService.registerMaintenance(id);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}/station/{stationId}")
    public ResponseEntity< SkateboardResponseDTO> assignToStation(@PathVariable Long id, @PathVariable Long stationId) {
        SkateboardResponseDTO res = skateboardService.assignToStation(id, stationId);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/nearby")
    public ResponseEntity<List< SkateboardResponseDTO>> findNearby(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "0.5") double radiusKm) {
        List< SkateboardResponseDTO> res = skateboardService.findNearby(lat, lon, radiusKm);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/report/km")
    public ResponseEntity<List< SkateboardResponseDTO>> reportByKm(@RequestParam double minKm) {
        List< SkateboardResponseDTO> res = skateboardService.findScootersWithMoreThanKm(minKm);
        return ResponseEntity.ok(res);
    }

    // (3) - Obtener monopatines en mantenimiento
    @GetMapping("/report/maintenance")
    public ResponseEntity<List< SkateboardResponseDTO>> getScootersNeedingMaintenance() {
        List< SkateboardResponseDTO> res = skateboardService.getScootersNeedingMaintenance();
        return ResponseEntity.ok(res);
    }
}
