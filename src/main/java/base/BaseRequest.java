package base;

import config.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.CustomLoggingFilter;

import java.io.File;

public class BaseRequest {
    private static final Logger log = LogManager.getLogger(BaseRequest.class);
    private static RequestSpecification requestSpec;

    private static void initRequestSpec() {
        int timeout = Integer.parseInt(
                ConfigReader.get("timeout") != null ? ConfigReader.get("timeout") : "5000"
        );
        try
        {
            System.out.println("Log4j config loaded from: " +
                    Thread.currentThread().getContextClassLoader()
                            .getResource("log4j2.xml"));
            RestAssuredConfig config = RestAssuredConfig.config()
                    .httpClient(HttpClientConfig.httpClientConfig()
                            .setParam("http.connection.timeout", timeout)
                            .setParam("http.socket.timeout", timeout)
                            .setParam("http.connection-manager.timeout", timeout)
                    );
            requestSpec = new RequestSpecBuilder()
                    .setBaseUri(ConfigReader.get("base.url"))
                    .setContentType(ContentType.JSON)
                    .addHeader("x-api-key", ConfigReader.get("api.key"))
                    .setConfig(config)
                    .addFilter(new CustomLoggingFilter())
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
