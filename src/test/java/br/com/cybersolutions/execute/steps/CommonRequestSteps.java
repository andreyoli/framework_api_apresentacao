package br.com.cybersolutions.execute.steps;

import br.com.cybersolutions.core.handlers.ResponseHandler;
import io.cucumber.java.en.And;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.lang.reflect.InvocationTargetException;

import static io.restassured.RestAssured.given;

public class CommonRequestSteps {

    @And("faço um {word} para {string}")
    public void requestTo(String requestType, String endpoint) {
        try {
            Object[] emptyArgsArray = {};
            RequestSpecification requestSpec = RestAssured.given().when();
            ResponseHandler.response = (Response) requestSpec.getClass().getMethod(requestType, String.class, Object[].class).invoke(requestSpec, endpoint, emptyArgsArray);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
