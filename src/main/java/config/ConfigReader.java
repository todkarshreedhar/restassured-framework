package config;

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

            log.info("======================================");
            log.info("Loaded environment: {}", env);
            log.info("Loaded config file: {}", fileName);
            log.info("User Base URL: {}", properties.getProperty("user.base.url"));
            log.info("Auth Base URL: {}", properties.getProperty("auth.base.url"));
            log.info("Payment Base URL: {}", properties.getProperty("payment.base.url"));
            log.info("======================================");


        } catch (IOException e) {
            log.error("Failed to load configuration file", e);
            throw new RuntimeException("Failed to load config", e);
        }
    }
    public static String get(String key) {
        String value = properties.getProperty(key);
        if(value == null) log.warn("WARNING: Key [" + key + "] returned null!");
        return value;
    }
}
