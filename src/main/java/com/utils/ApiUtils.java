package com.utils;

import aquality.selenium.core.logging.Logger;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import java.util.Map;

public class ApiUtils {
    private static final String BASE_PATH = Config.getInstance().getProperties("ApiUrl");
    private static ApiUtils instance;
    private final RequestSpecification specification = new RequestSpecBuilder()
            .setBaseUri(BASE_PATH)
            .build();
    private Response response;

    private ApiUtils() {}

    /**
     * Create an instance to get access to class methods
     * @return Class instance
     */
    public static ApiUtils getInstance() {
        if (instance == null) {
            instance = new ApiUtils();
            RestAssured.filters(new MyRequestFilter());
        }
        return instance;
    }

    /**
     * Send POST request to API
     * @param target URL of request
     * @param key The key of parameter
     * @param value The value of parameter to send
     */
    public void postRequest(String target, String key, String value) {
        response = RestAssured
                .given()
                .spec(specification)
                .queryParam(key, value)
                .post(target);
    }

    /**
     * Send POST request to API
     * @param target URL of request
     * @param param Parameters given to request in format key-value through Map<String, String>
     */
    public void postRequest(String target, Map<String, String> param) {
        response = RestAssured
                .given()
                .spec(specification)
                .params(param)
                .post(target);
    }

    /**
     * Get the status code of request:
     * 1xx - information;
     * 2xx - success;
     * 3xx - redirect;
     * 4xx - client error;
     * 5xx - server error
     * @return Status code
     */
    public int getStatusCode () {
        return response.statusCode();
    }

    /**
     * Get the Content type of the request
     * @return String representation of Content type
     */
    public String getContentType() {
        return response.contentType().split(";")[0];
    }

    /**
     * Get the request body from the API server
     * @return The String, that represents the request body
     */
    public String getBody() {
        return response.getBody().asString();
    }

    /**
     * Get the list of some value from API request
     * @param target The key to get list of values
     * @param <T> The type of value
     * @return List of values by key "target"
     */
    public <T extends Comparable<T>> List<T> getList(String target) {
        return response.jsonPath().getList(target);
    }

    /**
     * Filter request command and add logging
     */
    static class MyRequestFilter implements Filter {
        private final Logger logger = Logger.getInstance();

        @Override
        public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
            Response response = ctx.next(requestSpec, responseSpec);
            if (requestSpec.getMethod().equals("GET")) {
                logger.info(String.format("Getting request from %1$s", requestSpec.getURI()));
            } else if (requestSpec.getMethod().equals("POST")) {
                logger.info(String.format("Post request to %1$s", requestSpec.getURI()));
            }
            Logger.getInstance().info(String.format("Status code of request is: %1$s", response.statusCode()));
            if (response.statusCode() >= 400) {
                logger.error(String.format("%1$s => %2$s", requestSpec.getURI(), response.getStatusLine()));
            }
            return response;
        }
    }
}
