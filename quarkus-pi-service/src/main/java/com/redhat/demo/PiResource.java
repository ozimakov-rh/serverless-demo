package com.redhat.demo;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Duration;
import java.time.LocalTime;

@Path("/pi")
public class PiResource {

    @Inject
    PiCalculatorService piCalculatorService;

    @GET
    @Path("/{n}")
    @Produces(MediaType.APPLICATION_JSON)
    public PiResponse calcPi(@PathParam("n") int n) {
        var complexity = Math.pow(10, n);
        var start = LocalTime.now();
        var pi = piCalculatorService.calculatePi(complexity);
        var end = LocalTime.now();
        return new PiResponse(complexity, pi, Duration.between(start, end).toMillis());
    }

    public record PiResponse (double complexity, double pi, long duration) {
    }

}