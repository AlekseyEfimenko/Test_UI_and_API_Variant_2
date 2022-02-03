package com.data;

public enum Keys {
    VARIANT("variant");
    private final String key;

    Keys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
