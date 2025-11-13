package com.arqui.rateservice.controller;

import com.arqui.rateservice.dto.RateRequestDTO;
import com.arqui.rateservice.dto.RateResponseDTO;
import com.arqui.rateservice.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rates")
public class RateController {
    @Autowired
    private RateService rateService;

    @PostMapping("")
    public ResponseEntity<RateResponseDTO> save (@RequestBody RateRequestDTO req) {
        return ResponseEntity.ok(rateService.save(req));
    }

    @PutMapping("")
    public ResponseEntity<RateResponseDTO> update (@RequestBody RateRequestDTO req) {
        return ResponseEntity.ok(rateService.update(req));
    }

    @GetMapping("")
    public ResponseEntity<RateResponseDTO> fetchActualRate () {
        return ResponseEntity.ok(rateService.fetchActualRate());
    }
}
