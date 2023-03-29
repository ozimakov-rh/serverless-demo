package com.redhat.demo.function;

import io.quarkus.funqy.Funq;
import lombok.*;

import javax.inject.Inject;
import java.time.Duration;
import java.time.LocalTime;

/**
 * The Function class
 */
public class PiFunction {

    public final static String APP_VERSION_TAG = "func/v1";

    @Inject
    PiCalculatorService piCalculatorService;

    @Funq
    public PiOutput calcPi(PiInput input) {
        var complexity = Math.pow(10, input.n);
        var start = LocalTime.now();
        var pi = piCalculatorService.calculatePi(complexity);
        var end = LocalTime.now();
        return new PiOutput(complexity, pi, Duration.between(start, end).toMillis(), APP_VERSION_TAG);
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class PiInput {
        private int n;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class PiOutput {
        private double complexity;
        private double pi;
        private long duration;
        private String version;
    }

}
