package com.utils;

import aquality.selenium.core.logging.Logger;
import org.apache.commons.lang3.RandomStringUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class Config {
    private static Config instance;
    private final Properties properties = new Properties();
    private final Logger logger = Logger.getInstance();

    private Config() {}

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    /**
     * Get value from .properties file
     * @param key The key to get value
     * @return The value of .properties file in String format
     */
    public String getProperties(String key) {
        loadFile("config.properties");
        return properties.getProperty(key);
    }

    /**
     * Check if list is sorted
     * @param listOfStrings The list to check in
     * @param <T> The type of objects in the list
     * @return true - if list is sorted. Otherwise false
     */
    public <T extends Comparable<T>> boolean isSorted(List<T> listOfStrings) {
        if (listOfStrings.isEmpty() || listOfStrings.size() == 1) {
            return true;
        }

        Iterator<T> iter = listOfStrings.iterator();
        T current;
        T previous = iter.next();

        while (iter.hasNext()) {
            current = iter.next();
            if (previous.compareTo(current) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }

    /**
     * Reverse sort of list
     * @param list The list to bs sorted reverse
     * @param <T> Type of elements in the list
     */
    public <T> void reverseList(List<T> list) {
        Collections.reverse(list);
    }

    /**
     * Generate random String line
     * @param length The length of generated String
     * @return Random String
     */
    public String getRandomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    /**
     * Get Root path of project
     * @return Path in String format
     */
    public String getRootPath() {
        String path = "";
        try {
            path = Paths.get(Objects.requireNonNull(getClass().getResource("/")).toURI()).getParent().toString();
        } catch (URISyntaxException ex) {
            logger.error(ex.getMessage());
        }
        return path;
    }

    /**
     * Load properties from custom file to Properties object
     * @param src File, contains properties to load
     */
    private void loadFile(String src) {
        try (FileInputStream fileInputStream = new FileInputStream(getFile(src))) {
            properties.load(fileInputStream);
        } catch (IOException ex) {
            Logger.getInstance().error(String.format("File %s is not found%n%s", src, ex.getMessage()));
        }
    }

    /**
     * Get file, from resources folder
     * @param src path to file
     * @return File from resources folder
     */
    private File getFile(String src) {
        File file = null;
        try {
            file = new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource(src)).toURI());
        } catch (URISyntaxException ex) {
            logger.error(ex.getMessage());
        }
        return file;
    }
}
