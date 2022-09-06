package br.com.cybersolutions.execute.steps;

import br.com.cybersolutions.core.handlers.AttributeHandler;
import br.com.cybersolutions.core.handlers.ResponseHandler;
import br.com.cybersolutions.core.utils.Props;
import br.com.cybersolutions.core.utils.StringSyntax;
import com.google.gson.GsonBuilder;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.util.Map;

import static org.hamcrest.Matchers.is;

public class CommonResponseSteps {
    
    @Then("recebo a response {string} com o status {int}")
    public void receboResponse(String obj, int statusCode, DataTable dataTable) throws Exception {
        Map<String, String> values = dataTable.asMap(String.class, String.class);
        Object instance = Class.forName(Props.getBodyResponsePath() + "." + obj).getConstructor().newInstance();
        AttributeHandler.setAttribute(instance, values);
        String serializedBody = new GsonBuilder().serializeNulls().create().toJson(instance);
        JsonPath expectedJson = new JsonPath(serializedBody);

        ResponseHandler.response
                .then()
                .statusCode(statusCode)
                .contentType(ContentType.JSON)
                .body("", Matchers.is(expectedJson.getMap("")));
    }

    @Then("recebo a response com status {int} com os dados")
    public void receboResponseComOsDados(int statusCode, DataTable dataTable) {
        Map<String, String> values = dataTable.asMap(String.class, String.class);
        Map<String, Object> valuesWithSyntax = StringSyntax.setSyntax(values);

        ResponseHandler.response
                .then()
                .statusCode(statusCode)
                .contentType(ContentType.JSON)
        ;

        valuesWithSyntax.keySet().forEach(key -> ResponseHandler.response
                .then()
                .body(key, valuesWithSyntax.get(key) instanceof Matcher
                        ? (Matcher<?>) valuesWithSyntax.get(key)
                        : Matchers.is(valuesWithSyntax.get(key))));

    }

    @Then("recebo a response com status {int}")
    public void validoStatusDaResponse(int statusCode) {
        ResponseHandler.response.then().statusCode(statusCode);
    }

    @Then("salvo os dados da response")
    public void salvoDados(DataTable data) {
        Map<String, String> values = data.asMap(String.class, String.class);

        JsonPath responseJsonPath = ResponseHandler.response.then().extract().jsonPath();
        values.keySet().forEach(key -> ResponseHandler.data.put(key, responseJsonPath.get(values.get(key))));
    }
}
