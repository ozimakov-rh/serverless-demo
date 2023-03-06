package com.redhat.demo;

import com.redhat.demo.common.PiCalculator;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalTime;

@RestController
@RequestMapping("pi")
public class PiResourceController {

    @GetMapping(value = "/{n}", produces = "application/json")
    public PiResponse calcPi(@PathVariable int n) {
        var complexity = Math.pow(10, n);
        var start = LocalTime.now();
        var pi = PiCalculator.calculatePi(complexity);
        var end = LocalTime.now();
        return new PiResponse(complexity, pi, Duration.between(start, end).toMillis());
    }

    public record PiResponse(double complexity, double pi, long duration) {
    }

}
