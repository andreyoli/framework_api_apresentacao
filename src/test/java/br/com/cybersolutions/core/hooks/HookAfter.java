package br.com.cybersolutions.core.hooks;

import br.com.cybersolutions.core.handlers.ResponseHandler;
import io.cucumber.java.After;
import io.restassured.RestAssured;

public class HookAfter {

    @After
    public static void frameworkAfterEachTest() {
        RestAssured.requestSpecification = null;
        ResponseHandler.response = null;
        ResponseHandler.data.clear();
    }
}
