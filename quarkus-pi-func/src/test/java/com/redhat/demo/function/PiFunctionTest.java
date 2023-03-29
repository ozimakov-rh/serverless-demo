package com.redhat.demo.function;

import com.redhat.demo.function.PiFunction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.hamcrest.Matchers.*;

@QuarkusTest
public class PiFunctionTest {

    @Inject
    PiFunction piFunction;

    @Test
    void testFunction() {
        PiFunction.PiOutput output = piFunction.calcPi(new PiFunction.PiInput(5));
        Assertions.assertEquals(PiFunction.APP_VERSION_TAG, output.getVersion());
        Assertions.assertTrue((""+output.getPi()).startsWith("3.14"));
    }

    @Test
    public void testFunctionIntegration() {
        RestAssured.given().contentType("application/json")
                .body("{\"n\": 5 }")
                .header("ce-id", "42")
                .header("ce-specversion", "1.0")
                .post("/")
                .then().statusCode(200)
                .body("version", equalTo(PiFunction.APP_VERSION_TAG))
                .body("pi", notNullValue());
    }

}
