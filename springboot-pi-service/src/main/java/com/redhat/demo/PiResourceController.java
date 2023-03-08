package com.redhat.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalTime;

@RestController
@RequestMapping("pi")
public class PiResourceController {

    @Autowired
    private PiCalculatorService piCalculatorService;

    @GetMapping(value = "/{n}", produces = "application/json")
    public PiResponse calcPi(@PathVariable int n) {
        var complexity = Math.pow(10, n);
        var start = LocalTime.now();
        var pi = piCalculatorService.calculatePi(complexity);
        var end = LocalTime.now();
        return new PiResponse(complexity, pi, Duration.between(start, end).toMillis());
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class PiResponse {
        private double complexity;
        private double pi;
        private long duration;
    }

}
