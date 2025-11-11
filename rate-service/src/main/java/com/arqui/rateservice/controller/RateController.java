package com.arqui.rateservice.controller;

import com.arqui.rateservice.dto.RateReponseDTO;
import com.arqui.rateservice.service.RateService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController("/rate")
public class RateController {
    private RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @GetMapping("")
    public ResponseEntity<RateReponseDTO> fetchActualRate () {
        return ResponseEntity.ok(rateService.fetchActualRate());
    }
}
