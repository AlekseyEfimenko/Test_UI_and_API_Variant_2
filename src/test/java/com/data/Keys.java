package com.data;

public enum Keys {
    VARIANT("variant"),
    PROJECT_ID("projectId"),
    SESSION_ID("SID"),
    PROJECT_NAME("projectName"),
    TEST_NAME("testName"),
    METHOD_NAME("methodName"),
    ENVIRONMENT("env"),
    BROWSER("browser"),
    TEST_ID("testId"),
    CONTENT("content"),
    IS_EXCEPTION("isException"),
    CONTENT_TYPE("contentType");

    private final String key;

    Keys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
