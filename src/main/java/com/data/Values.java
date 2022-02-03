package com.data;

public enum Values {
    V_VARIANT("2");
    private final String value;

    Values(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
