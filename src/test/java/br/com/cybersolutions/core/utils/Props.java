package br.com.cybersolutions.core.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Props {

    private static String bodyRequestPath;
    private static String bodyResponsePath;
    private static String enumHeadersPath;
    private static String baseUrl;
    private static String basePath;
    private static boolean enableLogRequest;
    private static boolean enableLogResponse;

    static {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("src/test/resources/honey-options.properties"));

            enableLogRequest = Boolean.parseBoolean(props.getProperty("log.enable.request"));
            enableLogResponse = Boolean.parseBoolean(props.getProperty("log.enable.response"));
            baseUrl = props.getProperty("base.url");
            basePath = props.getProperty("base.path");
            bodyRequestPath = props.getProperty("body.request.path");
            bodyResponsePath = props.getProperty("body.response.path");
            enumHeadersPath = props.getProperty("enum.headers.path");

        } catch (FileNotFoundException e) {
            System.out.println("Verifique se o arquivo honey-options.properties existe na pasta resources");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Algum erro de IO ocorreu ... ");
            e.printStackTrace();
        }
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getBasePath() {
        return basePath;
    }

    public static boolean isEnableLogRequest() {
        return enableLogRequest;
    }

    public static boolean isEnableLogResponse() {
        return enableLogResponse;
    }

    public static String getBodyRequestPath() {
        return bodyRequestPath;
    }

    public static String getBodyResponsePath() {
        return bodyResponsePath;
    }

    public static String getEnumHeadersPath() {
        return enumHeadersPath;
    }
}
