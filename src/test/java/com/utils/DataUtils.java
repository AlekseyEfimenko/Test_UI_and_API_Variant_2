package com.utils;

import static com.cucumber.ScenarioContext.getContext;
import aquality.selenium.core.logging.Logger;
import com.cucumber.Context;
import com.data.Keys;
import com.data.ContentType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.pojo.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DataUtils {
    private static DataUtils instance;
    private final Map<String, String> mapParam = new HashMap<>();

    private DataUtils() {}

    public static DataUtils getInstance() {
        if (instance == null) {
            instance = new DataUtils();
        }
        return instance;
    }

    /**
     * Get start URL for test
     * @return URL in String format
     */
    public String getStartURL() {
        return Config.getInstance().getProperties("WebUrl");
    }

    /**
     * Get list of Test objects from body request (in String format), represented in json format
     * @return List of Test objects
     */
    public List<Test> getListOfTests() {
        List<Test> tests = new ArrayList<>();
        try {
            Type collectionType = new TypeToken<List<Test>>() {
            }.getType();
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            tests = gson.fromJson(ApiUtils.getInstance().getBody(), collectionType);
        } catch (JsonSyntaxException ex) {
            Logger.getInstance().error(ex.getMessage());
        }
        return tests;
    }

    /**
     * Set parameters for POST request to add new test for current project
     * @return Map<key, value> of parameters
     */
    public Map<String, String> getParametersForTestAdd() {
        mapParam.clear();
        mapParam.put(Keys.SESSION_ID.getKey(), getContext(Context.SESSION_ID));
        mapParam.put(Keys.PROJECT_NAME.getKey(), getContext(Context.PROJECT_NAME));
        mapParam.put(Keys.TEST_NAME.getKey(), getContext(Context.TEST_NAME));
        mapParam.put(Keys.METHOD_NAME.getKey(), getContext(Context.METHOD_NAME));
        mapParam.put(Keys.ENVIRONMENT.getKey(), getContext(Context.HOST));
        mapParam.put(Keys.BROWSER.getKey(), getContext(Context.BROWSER));
        return mapParam;
    }

    /**
     * Set parameters for POST request to add logs for current test
     * @return Map<key, value> of parameters
     */
    public Map<String, String> getParametersForLogsAdd() {
        mapParam.clear();
        mapParam.put(Keys.TEST_ID.getKey(), getContext(Context.TEST_ID));
        mapParam.put(Keys.CONTENT.getKey(), getContext(Context.LOGS));
        mapParam.put(Keys.IS_EXCEPTION.getKey(), getContext(Context.IS_EXCEPTION));
        return mapParam;
    }

    /**
     * Set parameters for POST request to add screenshot for current test
     * @return Map<key, value> of parameters
     */
    public Map<String, String> getParametersForScreenshotAdd() {
        mapParam.clear();
        mapParam.put(Keys.TEST_ID.getKey(), getContext(Context.TEST_ID));
        mapParam.put(Keys.CONTENT.getKey(), getContext(Context.SCREENSHOT));
        mapParam.put(Keys.CONTENT_TYPE.getKey(), ContentType.CONTENT_TYPE.getValue());
        return mapParam;
    }

    /**
     * Convert logs from file.log to String format
     * @param file File, contains logs
     * @return String of logs
     */
    public String printLog(String file) {
        StringBuilder st = new StringBuilder();
        try (Scanner in = new Scanner(new File(file))) {
            while (in.hasNext()) {
                st.append(in.nextLine()).append("\n");
            }
        } catch (FileNotFoundException ex) {
            Logger.getInstance().error(ex.getMessage());
        }
        return st.toString();
    }

    /**
     * Creates a new print stream
     * @param name Name of the file to use as the destination of this print stream
     * @return New print stream
     */
    public PrintStream createFile(String name) {
        PrintStream ps = null;
        try {
            ps = new PrintStream(name);
        } catch (FileNotFoundException ex) {
            Logger.getInstance().error(ex.getMessage());
        }
        return ps;
    }
}
