package com.data;

public enum EndPoints {
    TOKEN_TARGET("token/get"),
    GET_TESTS("test/get/json"),
    ADD_TEST("test/put"),
    ADD_LOGS("test/put/log"),
    ADD_CONTENT("test/put/attachment");

    private final String value;

    EndPoints(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
