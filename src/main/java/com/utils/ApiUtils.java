package com.utils;

import aquality.selenium.core.logging.Logger;
import com.data.Keys;
import com.data.Values;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class ApiUtils {
    private static final String BASE_PATH = Config.getInstance().getProperties("ApiUrl");
    private static ApiUtils instance;
    private Response response;

    private ApiUtils() {}

    public static ApiUtils getInstance() {
        if (instance == null) {
            instance = new ApiUtils();
            RestAssured.filters(new MyRequestFilter());
        }
        return instance;
    }

    public void postRequest(String target) {
        response = RestAssured
                .given()
                .queryParam(Keys.VARIANT.getKey(), Values.V_VARIANT.getValue())
                .post(String.format("%1$s%2$s", BASE_PATH, target));
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
     * Get the request body from the API server
     * @return The String, that represents the request body
     */
    public String getBody() {
        return response.getBody().asString();
    }

    static class MyRequestFilter implements Filter {

        @Override
        public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
            Response response = ctx.next(requestSpec, responseSpec);
            if (requestSpec.getMethod().equals("GET")) {
                Logger.getInstance().info(String.format("Getting request from %1$s", requestSpec.getURI()));
            } else if (requestSpec.getMethod().equals("POST")) {
                Logger.getInstance().info(String.format("Post request to %1$s", requestSpec.getURI()));
            }
            Logger.getInstance().info(String.format("Status code of request is: %1$s", response.statusCode()));
            if (response.statusCode() >= 400) {
                Logger.getInstance().error(String.format("%1$s => %2$s", requestSpec.getURI(), response.getStatusLine()));
            }
            return response;
        }
    }
}
