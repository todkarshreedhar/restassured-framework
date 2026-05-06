package config;


import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties = new Properties();
    private static final Logger log = LogManager.getLogger(ConfigReader.class);

    static {
        try{
            String env = System.getProperty("env", "dev");
            String fileName = "config-"+env+".properties";
            InputStream is = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream(fileName);
            // Inside ConfigReader.java static block
            if (is == null) {
                throw new RuntimeException("CRITICAL: config.properties not found in src/main/resources");
            }
            properties.load(is);
            System.out.println("Loaded environment: "+ env);
            log.info("======================================");
            log.info("Loaded environment: {}", env);
            log.info("Loaded config file: {}", fileName);
            log.info("Base URL: {}", properties.getProperty("base.url"));
            log.info("======================================");


        } catch (IOException e) {
            log.error("Failed to load configuration file", e);
            throw new RuntimeException("Failed to load config", e);
        }
    }
    public static String get(String key) {
        String value = properties.getProperty(key);
        if(value == null) System.err.println("WARNING: Key [" + key + "] returned null!");
        return value;
    }
}
