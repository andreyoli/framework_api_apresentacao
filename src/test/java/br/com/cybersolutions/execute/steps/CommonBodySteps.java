package br.com.cybersolutions.execute.steps;

import br.com.cybersolutions.core.handlers.AttributeHandler;
import br.com.cybersolutions.core.utils.Props;
import com.google.gson.GsonBuilder;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class CommonBodySteps {

    @And("adiciono o body do tipo {string} alterando os dados")
    public void addBody(String obj, DataTable dataTable) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Map<String, String> values = dataTable.asMap(String.class, String.class);
        Object instance = Class.forName(Props.getBodyRequestPath() + "." + obj).getConstructor().newInstance();
        AttributeHandler.setAttribute(instance, values);
        String serializedBody = new GsonBuilder().serializeNulls().create().toJson(instance);
        RestAssured.requestSpecification.contentType(ContentType.JSON).body(serializedBody);
    }

    @And("adiciono o body do tipo {string}")
    public void addBody(String obj) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Object classLegal = Class.forName(Props.getBodyRequestPath() + "." + obj).getConstructor().newInstance();
        String serializedBody = new GsonBuilder().serializeNulls().create().toJson(classLegal);
        RestAssured.requestSpecification.contentType(ContentType.JSON).body(serializedBody);
    }

    @And("limpo o body adicionado anteriormente")
    public void cleanBody() {
        RestAssured.requestSpecification.body("{}");
    }
}
