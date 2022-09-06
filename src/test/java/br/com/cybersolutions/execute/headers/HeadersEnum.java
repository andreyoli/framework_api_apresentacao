package br.com.cybersolutions.execute.headers;

import br.com.cybersolutions.core.interfaces.EnumHeadersInterface;
import io.restassured.http.Header;
import io.restassured.http.Headers;

public enum HeadersEnum implements EnumHeadersInterface {

    COM_AUTH {
        @Override
        public Headers setHeaders() {
            return new Headers(new Header("Authorization", "Bearer e894c69acfaa97fe3dbc4becfbf9d96b4e6f48f13e3cfe34d66453ff3c159a04"));
        }
    }
}
