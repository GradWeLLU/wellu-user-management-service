package com.wellu.usermanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/health")
    public String health(){
        return "ok kokomama";
    }

    @GetMapping("/okay")
    public String okay(){
        return "okay!";
    }
}

