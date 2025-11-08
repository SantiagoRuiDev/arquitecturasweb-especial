package com.arqui.travelservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TravelServiceApplication {

    // Su objetivo principal es crear y controlar el ciclo de vida de un viaje
    public static void main(String[] args) {
        SpringApplication.run(TravelServiceApplication.class, args);
    }

}