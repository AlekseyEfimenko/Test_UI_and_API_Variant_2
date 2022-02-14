package com.data;

public enum StatusCode {
    SUCCESS(200);

    private final int value;

    StatusCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
