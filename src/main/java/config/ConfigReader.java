package config;


import io.restassured.RestAssured;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try{
            InputStream is = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");
            // Inside ConfigReader.java static block
            if (is == null) {
                throw new RuntimeException("CRITICAL: config.properties not found in src/main/resources");
            }
            else
            {
                properties = new Properties();
                properties.load(is);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String get(String key) {
        String value = properties.getProperty(key);
        if(value == null) System.err.println("WARNING: Key [" + key + "] returned null!");
        return value;
    }
}
