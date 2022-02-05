package com.utils;

import aquality.selenium.core.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pojo.Test;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import java.lang.reflect.Type;
import java.util.List;

public class ApiUtils {
    private static final String BASE_PATH = Config.getInstance().getProperties("ApiUrl");
    private static final String CONTENT_TYPE = "Content-Type";
    private static ApiUtils instance;
    private final Logger logger = Logger.getInstance();
    private Response response;

    private ApiUtils() {}

    public static ApiUtils getInstance() {
        if (instance == null) {
            instance = new ApiUtils();
            RestAssured.filters(new MyRequestFilter());
        }
        return instance;
    }

    public void postRequest(String target, String key, String value) {
        response = RestAssured
                .given()
                .header(CONTENT_TYPE, ContentType.JSON)
                .queryParam(key, value)
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

    public List<Test> getListOfTests() {
            Type collectionType = new TypeToken<List<Test>>() {}.getType();
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            return gson.fromJson(getBody(), collectionType);
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
