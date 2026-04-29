package base;

import config.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.OutputStream;
import java.io.PrintStream;

public class BaseRequest {
    private static final Logger log = LogManager.getLogger(BaseRequest.class);
    private static RequestSpecification requestSpec;

    private static void initRequestSpec() {
        try
        {
            PrintStream logStream = new PrintStream(new OutputStream() {
                private StringBuilder buffer = new StringBuilder();

                @Override
                public void write(int b) {
                    if (b == '\n') {
                        log.info(buffer.toString());
                        buffer.setLength(0);
                    } else {
                        buffer.append((char) b);
                    }
                }
            }, true);

            System.out.println("api.key"+ConfigReader.get("api.key"));
            requestSpec = new RequestSpecBuilder()
                    .setBaseUri(ConfigReader.get("base.url"))
                    .setContentType(ContentType.JSON)
                    .addHeader("x-api-key", ConfigReader.get("api.key"))
                    //.addFilter(new RequestLoggingFilter(logStream))
                    //.addFilter(new ResponseLoggingFilter(logStream))
                    .build();

            log.info("RequestSpecification initialized successfully");

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize BaseRequest", e);
        }
    }

    public static synchronized RequestSpecification getRequestSpec() {
        if (requestSpec == null) {
            initRequestSpec();
        }
        return requestSpec;
    }

}
