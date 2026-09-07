package com.tcc.rateiofacil.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class PingController {

    @GetMapping("/api/ping")
    public String ping() {
        return "RateioFacil no ar em " + LocalDateTime.now();
    }
}