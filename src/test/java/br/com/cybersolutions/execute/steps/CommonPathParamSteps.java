package br.com.cybersolutions.execute.steps;

import br.com.cybersolutions.core.utils.StringSyntax;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;

import java.util.Map;

public class CommonPathParamSteps {

    @And("adiciono os pathParams")
    public void addPathParams(DataTable dataTable) {
        Map<String, String> pathParams = dataTable.asMap(String.class, String.class);
        Map<String, Object> pathParamsWithSyntax = StringSyntax.setSyntax(pathParams);

        pathParamsWithSyntax.keySet().forEach(pathParam -> RestAssured.requestSpecification.pathParam(pathParam, pathParamsWithSyntax.get(pathParam)));
    }

    @And("removo todos os pathParams")
    public void removePathParams() {
        FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) RestAssured.requestSpecification.given();
        Map<String, String> actualPathParams = filterableRequestSpecification.getPathParams();
        actualPathParams.keySet().forEach(filterableRequestSpecification::removePathParam);
    }
}
