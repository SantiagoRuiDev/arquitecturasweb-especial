package com.arqui.stationservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationRequestDTO {
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private Integer capacity;
}