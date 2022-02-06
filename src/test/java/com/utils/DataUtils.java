package com.utils;

import static com.cucumber.ScenarioContext.getContext;
import com.cucumber.Context;
import com.data.Keys;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pojo.Test;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataUtils {
    private static DataUtils instance;

    private DataUtils() {}

    public static DataUtils getInstance() {
        if (instance == null) {
            instance = new DataUtils();
        }
        return instance;
    }

    public String getStartURL() {
        return Config.getInstance().getProperties("WebUrl");
    }

    public List<Test> getListOfTests() {
        Type collectionType = new TypeToken<List<Test>>() {}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(ApiUtils.getInstance().getBody(), collectionType);
    }

    public Map<String, String> getParameters() {
        Map<String, String> mapParam = new HashMap<>();
        mapParam.put(Keys.SESSION_ID.getKey(), getContext(Context.SESSION_ID));
        mapParam.put(Keys.PROJECT_NAME.getKey(), Config.getInstance().getProperties("project"));
        mapParam.put(Keys.TEST_NAME.getKey(), getContext(Context.TEST_NAME));
        mapParam.put(Keys.METHOD_NAME.getKey(), getContext(Context.METHOD_NAME));
        mapParam.put(Keys.ENVIRONMENT.getKey(), getContext(Context.HOST));
        mapParam.put(Keys.BROWSER.getKey(), getContext(Context.BROWSER));
        return mapParam;
    }
}
