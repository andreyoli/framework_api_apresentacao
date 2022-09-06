package br.com.cybersolutions.core.hooks;

import br.com.cybersolutions.core.utils.Props;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class HookBefore {

    public static void frameworkInitialSetup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = Props.getBaseUrl();
        RestAssured.basePath = Props.getBasePath();

        if (Props.isEnableLogRequest()) {
            RestAssured.filters(new RequestLoggingFilter());
        }

        if (Props.isEnableLogResponse()) {
            RestAssured.filters(new ResponseLoggingFilter());
        }
    }

    @Before
    public static void frameworkBeforeEachTest() {
        RestAssured.requestSpecification = new RequestSpecBuilder().build();
    }
}
