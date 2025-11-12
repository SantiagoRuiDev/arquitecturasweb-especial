package com.arqui.paymentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillReportResponseDTO {
    private Integer startMonth;
    private Integer endMonth;
    private Integer year;
    private Double total;
}
