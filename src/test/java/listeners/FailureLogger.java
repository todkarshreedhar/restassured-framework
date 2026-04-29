package listeners;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class FailureLogger implements ITestListener {
    private static final Logger log = LogManager.getLogger(FailureLogger.class);

    @Override
    public void onTestFailure(ITestResult result) {

        log.error("========== TEST FAILED ==========");
        log.error("Test Name: {}", result.getName());

        Object resObj = result.getAttribute("response");

        if (resObj instanceof Response response) {

            log.error("Status Code: {}", response.getStatusCode());
            log.error("Response Body:\n{}", response.getBody().asPrettyString());
            log.error("Headers: {}", response.getHeaders());

        } else {
            log.error("No response captured");
        }

        log.error("================================");
    }

}
