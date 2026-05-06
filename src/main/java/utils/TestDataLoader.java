package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;

public class TestDataLoader {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log =
            LogManager.getLogger(TestDataLoader.class);
    private TestDataLoader() {
        // Prevent object creation
    }
    public static <T> T load(String filePath, Class<T> clazz)
    {
        try {
            InputStream inputStream = TestDataLoader.class
                    .getClassLoader()
                    .getResourceAsStream(filePath);

            if (inputStream == null) {
                throw new RuntimeException("Test data file not found: " + filePath);
            }
            log.info("Loaded test data file: {}", filePath);
            return objectMapper.readValue(inputStream, clazz);


        }catch (Exception e) {
            throw new RuntimeException(
                    "Failed to load test data from: " + filePath,
                    e
            );
        }
    }

}
