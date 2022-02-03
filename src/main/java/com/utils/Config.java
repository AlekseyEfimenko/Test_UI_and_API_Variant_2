package com.utils;

import aquality.selenium.core.logging.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Properties;

public class Config {
    private final Properties properties = new Properties();
    private static Config instance;

    private Config() {}

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public String getStartURL() {
        return getProperties("WebUrl");
    }

    public String getProperties(String key) {
        loadFile("config.properties");
        return properties.getProperty(key);
    }

    private void loadFile(String src) {
        try (FileInputStream fileInputStream = new FileInputStream(getFile(src))) {
            properties.load(fileInputStream);
        } catch (IOException ex) {
            Logger.getInstance().error(String.format("File %s is not found%n%s", src, ex.getMessage()));
        }
    }

    private File getFile(String src) {
        File file = null;
        try {
            file = new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource(src)).toURI());
        } catch (URISyntaxException ex) {
            Logger.getInstance().error(ex.getMessage());
        }
        return file;
    }
}
