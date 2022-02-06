package com.data;

public enum Keys {
    VARIANT("variant"),
    PROJECT_ID("projectId"),
    SESSION_ID("SID"),
    PROJECT_NAME("projectName"),
    TEST_NAME("testName"),
    METHOD_NAME("methodName"),
    ENVIRONMENT("env"),
    BROWSER("browser");

    private final String key;

    Keys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
