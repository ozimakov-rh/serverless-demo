package com.redhat.demo.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PiCalculatorTest {

    @Test
    public void calculatePi() {
        assertCloseEnough(0, PiCalculator.calculatePi(1), 0.001);
        assertCloseEnough(4, PiCalculator.calculatePi(2), 0.001);
        assertCloseEnough(Math.PI, PiCalculator.calculatePi(100000), 0.001);
        assertCloseEnough(Math.PI, PiCalculator.calculatePi(1000000), 0.00001);
    }

    private void assertCloseEnough(double v1, double v2, double precision) {
        var diff = Math.abs(v1 - v2);
        assertTrue(diff < precision, () ->
                String.format("Values %f and %f are not close enough. The diff is %f but precision is %f.", v1, v2, diff, precision)
        );
    }

}