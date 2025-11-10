package controller;

import dto.SkateboardRequestDTO;
import dto.SkateboardResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.SkateboardService;

import java.util.List;

@RestController
@RequestMapping("/skateboards")
public class SkateboardController {

    @Autowired
    private SkateboardService skateboardService;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean res = skateboardService.delete(id);
        return res ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/maintenance")
    public ResponseEntity< SkateboardResponseDTO> registerMaintenance(@PathVariable Long id) {
       SkateboardResponseDTO res = skateboardService.registerMaintenance(id);
        return ResponseEntity.ok(res);
    }

    // 2️⃣ Place scooter in a station
    @PutMapping("/{id}/station/{stationId}")
    public ResponseEntity< SkateboardResponseDTO> assignToStation(@PathVariable Long id, @PathVariable Long stationId) {
        SkateboardResponseDTO res = skateboardService.assignToStation(id, stationId);
        return ResponseEntity.ok(res);
    }

    // 3️⃣ Get scooters near a given location
    @GetMapping("/nearby")
    public ResponseEntity<List< SkateboardResponseDTO>> findNearby(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "0.5") double radiusKm) {
        List< SkateboardResponseDTO> res = skateboardService.findNearby(lat, lon, radiusKm);
        return ResponseEntity.ok(res);
    }

    // 4️⃣ Report: scooters with more than X kilometers
    @GetMapping("/report/km")
    public ResponseEntity<List< SkateboardResponseDTO>> reportByKm(@RequestParam double minKm) {
        List< SkateboardResponseDTO> res = skateboardService.findScootersWithMoreThanKm(minKm);
        return ResponseEntity.ok(res);
    }

    // 5️⃣ Report: scooters needing maintenance
    @GetMapping("/report/maintenance")
    public ResponseEntity<List< SkateboardResponseDTO>> getScootersNeedingMaintenance() {
        List< SkateboardResponseDTO> res = skateboardService.getScootersNeedingMaintenance();
        return ResponseEntity.ok(res);
    }
}
