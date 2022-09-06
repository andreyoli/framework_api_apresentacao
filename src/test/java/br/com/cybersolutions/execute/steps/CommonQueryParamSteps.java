package br.com.cybersolutions.execute.steps;

import br.com.cybersolutions.core.utils.StringSyntax;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;

import java.util.Map;

public class CommonQueryParamSteps {

    @And("adiciono os queryParams")
    public void addQueryParams(DataTable dataTable) {
        Map<String, String> queryParams = dataTable.asMap(String.class, String.class);
        Map<String, Object> queryParamsWithSyntax = StringSyntax.setSyntax(queryParams);

        queryParamsWithSyntax.keySet().forEach(queryParam -> RestAssured.requestSpecification.queryParam(queryParam, queryParamsWithSyntax.get(queryParam)));
    }

    @And("removo todos os queryParams")
    public void removeQueryParams() {
        FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) RestAssured.requestSpecification.given();
        Map<String, String> actualQueryParams = filterableRequestSpecification.getQueryParams();
        actualQueryParams.keySet().forEach(filterableRequestSpecification::removeQueryParam);
    }
}
