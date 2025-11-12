package com.arqui.paymentservice.controller;

import com.arqui.paymentservice.dto.response.BillReportResponseDTO;
import com.arqui.paymentservice.service.PaymentBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class PaymentBillController {
    @Autowired
    private PaymentBillService paymentBillService;

    @GetMapping("/earnings-report")
    public ResponseEntity<BillReportResponseDTO> getTotalEarningsByPeriod (@RequestParam Integer year, @RequestParam Integer startMonth, @RequestParam Integer endMonth) {
        BillReportResponseDTO res = paymentBillService.getTotalEarningsByPeriod(year, startMonth, endMonth);
        return  ResponseEntity.ok().body(res);
    }
}
