package com.redhat.demo;

import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class PiResourceTest {

    @Test
    public void testPiEndpoint() {
        given()
                .when().get("/pi/5")
                .then()
                .statusCode(200)
                .body("pi", notNullValue());
    }

}