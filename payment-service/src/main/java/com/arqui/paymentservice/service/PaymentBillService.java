package com.arqui.paymentservice.service;

import com.arqui.paymentservice.dto.response.BillReportResponseDTO;
import com.arqui.paymentservice.repository.PaymentBillRepository;
import org.springframework.stereotype.Service;


@Service
public class PaymentBillService {
    private final PaymentBillRepository paymentBillRepository;

    public PaymentBillService(PaymentBillRepository paymentBillRepository) {
        this.paymentBillRepository = paymentBillRepository;
    }

    public BillReportResponseDTO getTotalEarningsByPeriod (Integer year, Integer startMonth, Integer endMonth) {
        BillReportResponseDTO dto = new BillReportResponseDTO();
        dto.setYear(year);
        dto.setStartMonth(startMonth);
        dto.setEndMonth(endMonth);
        dto.setTotal(paymentBillRepository.getTotalEarningsByPeriod(year, startMonth, endMonth));
        return dto;
    }
}
