package utils;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomLoggingFilter implements Filter {

    private static final Logger log = LogManager.getLogger(CustomLoggingFilter.class);

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {

        // ===== REQUEST =====
        log.info("🔥 FILTER EXECUTED 🔥");
        log.info("========== API REQUEST ==========");
        log.info("Method: {}", requestSpec.getMethod());
        log.info("URI: {}", requestSpec.getURI());

        requestSpec.getHeaders().forEach(header -> {
            if (LogUtils.isSensitive(header.getName())) {
                log.info("{}: {}", header.getName(), LogUtils.mask(header.getValue()));
            } else {
                log.info("{}: {}", header.getName(), header.getValue());
            }
        });

        if (requestSpec.getBody() != null) {
            Object body = requestSpec.getBody();

            try {
                log.info("Request Body: {}", body.toString());
            } catch (Exception e) {
                log.info("Request Body: [Unable to print safely]");
            }
        }

        // Execute request
        Response response = ctx.next(requestSpec, responseSpec);

        // ===== RESPONSE =====
        log.info("========== API RESPONSE ==========");
        log.info("Status Code: {}", response.getStatusCode());
        log.info("Response Body: {}", response.getBody().asPrettyString());

        return response;
    }
}