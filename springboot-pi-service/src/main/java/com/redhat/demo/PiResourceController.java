package com.redhat.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public record PiResponse (double complexity, double pi, long duration) {
    }

}
