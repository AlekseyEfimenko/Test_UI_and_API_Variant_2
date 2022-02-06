package com.data;

public enum Values {
    V_VARIANT("2"),
    V_TRUE("true"),
    V_FALSE("false"),
    V_CONTENT_TYPE("image/png");

    private final String value;

    Values(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
