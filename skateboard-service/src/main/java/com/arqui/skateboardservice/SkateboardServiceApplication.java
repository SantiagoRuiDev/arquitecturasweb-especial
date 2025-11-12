package com.arqui.skateboardservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SkateboardServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkateboardServiceApplication.class, args);
    }

}
