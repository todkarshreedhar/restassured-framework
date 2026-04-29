package tests;

import client.APIClient;
import constants.Endpoints;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import models.request.UserRequest;
import models.response.UserResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class SampleTest {

    Logger log = LogManager.getLogger(SampleTest.class);

    @Test
    public void getMultipleUsersTest()
    {
        Response response = APIClient.sendRequest("GET", Endpoints.GET_USERS, null);
        Reporter.getCurrentTestResult().setAttribute("response", response);
        response.then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("data",notNullValue());
    }
    @Test
    public void getSingleUserTest()
    {
        Response response = APIClient.sendRequest("GET", Endpoints.GET_SINGLE_USER, null);
        Reporter.getCurrentTestResult().setAttribute("response", response);
        response.then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("data.first_name",equalTo("Janet"));

    }
    @Test
    public void createUserTest()
    {
        UserRequest request = new UserRequest("Shreedhar", "Automation Engineer");
        Response response = APIClient.sendRequest("POST", Endpoints.CREATE_USER, request);
        Reporter.getCurrentTestResult().setAttribute("response", response);
        UserResponse responseBody = response.as(UserResponse.class);
        response.then()
                .log().ifValidationFails()
                .statusCode(201);
        // POJO validation (clean + type-safe)
        Assert.assertEquals(responseBody.getName(), "Shreedhar");
        Assert.assertEquals(responseBody.getJob(), "Automation Engineer");
        Assert.assertNotNull(responseBody.getId());
    }

    @Test
    public void updatePartialUserTest() {
        UserRequest request = new UserRequest();
        request.setJob("Lead Engineer");// Only partial update
        Response response = APIClient.sendRequest("PATCH", Endpoints.UPDATE_PARTIAL_USER, request);
        Reporter.getCurrentTestResult().setAttribute("response", response);
        UserResponse responseBody = response.as(UserResponse.class);
        response.then()
                .log().ifValidationFails()
                .statusCode(200);

        Assert.assertEquals(responseBody.getJob(), "Lead Engineer");
    }

    @Test
    public void updateCompleteUserTest() {

        UserRequest request = new UserRequest();
        request.setName("UpdatedName");
        Response response = APIClient.sendRequest("PUT", Endpoints.UPDATE_COMPLETE_USER, request);
        Reporter.getCurrentTestResult().setAttribute("response", response);
        UserResponse responseBody = response.as(UserResponse.class);
        response.then()
                .log().ifValidationFails()
                .statusCode(200);
        Assert.assertEquals(responseBody.getName(), "UpdatedName");
    }

    @Test
    public void deleteUserTest() {
        Response response = APIClient.sendRequest("DELETE", Endpoints.DELETE_USER, null);
        Reporter.getCurrentTestResult().setAttribute("response", response);
        response.then()
                .log().ifValidationFails()
                .statusCode(204);
    }


}
