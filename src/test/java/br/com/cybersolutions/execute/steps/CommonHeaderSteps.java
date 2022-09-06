package br.com.cybersolutions.execute.steps;

import br.com.cybersolutions.core.interfaces.EnumHeadersInterface;
import br.com.cybersolutions.core.utils.Props;
import br.com.cybersolutions.core.utils.StringSyntax;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonHeaderSteps {

    private static Map<String, EnumHeadersInterface> mapEnums;

    @And("utilizo os headers {string}")
    public void addHeaders(String header) throws Exception {
        if (mapEnums == null) {
            Class<?> clazz = Class.forName(Props.getEnumHeadersPath());
            EnumHeadersInterface[] enumArray = (EnumHeadersInterface[]) clazz.getEnumConstants();
            mapEnums = new HashMap<>();

            Arrays.stream(enumArray).forEach(e -> {
                mapEnums.put(e.toString(), e);
            });
        }

        header = header.toUpperCase();

        if (mapEnums.containsKey(header)) {
            RestAssured.requestSpecification.headers(mapEnums.get(header).setHeaders());
        } else {
            throw new NullPointerException("O Enum " + header + " não existe");
        }
    }

    @And("removo todos os headers")
    public void removeAllHeaders() {
        ((FilterableRequestSpecification) RestAssured.requestSpecification.given()).removeHeaders();
    }

    @And("removo os headers")
    public void removeHeaders(DataTable dataTable) {
        List<String> headers = dataTable.asList();
        FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) RestAssured.requestSpecification.given();

        headers.forEach(key -> {
            if (!filterableRequestSpecification.getHeaders().hasHeaderWithName(key)) {
                throw new IllegalArgumentException("Não foi encontrado um header chamado " + key);
            }

            filterableRequestSpecification.removeHeader(key);
        });
    }

    @And("adiciono os headers")
    public void addHeaders(DataTable dataTable) {
        Map<String, String> headers = dataTable.asMap(String.class, String.class);
        Map<String, Object> headersWithSyntax = StringSyntax.setSyntax(headers);

        headersWithSyntax.keySet().forEach(key -> {
            RestAssured.requestSpecification.header(key, headers.get(key));
        });
    }
}
