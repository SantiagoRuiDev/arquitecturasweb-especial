package com.arqui.travelservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pause {

    private String id; // puedes generar un UUID en el service

    private LocalDateTime startPause;
    private LocalDateTime endPause;
    private boolean exceededTimeLimit;

}