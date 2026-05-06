package base;

import config.ConfigManager;
import constants.ServiceType;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.CustomLoggingFilter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BaseRequest {
    private static final Logger log = LogManager.getLogger(BaseRequest.class);
    private static final Map<ServiceType, RequestSpecification> requestSpecs
            = new ConcurrentHashMap<>();
    private BaseRequest() {
        // Prevent object creation
    }
    private static void initRequestSpec(ServiceType serviceType) {
        int timeout = ConfigManager.getTimeout();
        try
        {
            RestAssuredConfig config = RestAssuredConfig.config()
                    .httpClient(HttpClientConfig.httpClientConfig()
                            .setParam("http.connection.timeout", timeout)
                            .setParam("http.socket.timeout", timeout)
                            .setParam("http.connection-manager.timeout", timeout)
                    );
            RequestSpecification requestSpec = new RequestSpecBuilder()
                    .setBaseUri(ConfigManager.getBaseUrl(serviceType))
                    .setContentType(ContentType.JSON)
                    .addHeader("x-api-key", ConfigManager.getApiKey())
                    .setConfig(config)
                    .addFilter(new CustomLoggingFilter())
                    .build();
            requestSpecs.put(serviceType, requestSpec);

            log.info("RequestSpecification initialized successfully");

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize BaseRequest", e);
        }
    }

    public static synchronized RequestSpecification getRequestSpec(ServiceType serviceType) {
        if (!requestSpecs.containsKey(serviceType)) {
            initRequestSpec(serviceType);
        }

        return requestSpecs.get(serviceType);
    }

}
