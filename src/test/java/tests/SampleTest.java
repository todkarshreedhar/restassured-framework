package tests;

import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import models.request.UserRequest;
import models.response.UserResponse;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import services.UserService;
import utils.AssertUtils;

public class SampleTest {



    @Test
    public void getMultipleUsersTest()
    {
        Response response = UserService.getAllUsersRaw();
        Reporter.getCurrentTestResult().setAttribute("response", response);
        response.then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("data",notNullValue());
    }
    @Test
    public void getSingleUserTest()
    {
        Response response = UserService.getUserRaw(3);
        Reporter.getCurrentTestResult().setAttribute("response", response);
        response.then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("data.first_name",equalTo("Emma"));

    }
    @Test
    public void createUserTest()
    {
        // Arrange
        UserRequest request = new UserRequest("Shreedhar", "Automation Engineer");
        // Act
        Response response = UserService.createUserRaw(request);
        UserResponse responseBody = response.as(UserResponse.class);
        // Attach response (for FailureLogger)
        Reporter.getCurrentTestResult().setAttribute("response", response);

        // Assert
        AssertUtils.assertEquals(responseBody.getName(), "Shreedhar", "Name mismatch");
        AssertUtils.assertEquals(responseBody.getJob(), "Automation Engineer", "Job value mismatch");
        AssertUtils.assertNotNull(responseBody.getId(), "ID should not be null");
    }

    @Test
    public void updatePartialUserTest() {

        UserRequest request = new UserRequest();
        request.setJob("Lead Engineer");// Only partial update
        Response response = UserService.updatePartialUserRaw(3, request);
        UserResponse responseBody = response.as(UserResponse.class);
        Reporter.getCurrentTestResult().setAttribute("response", response);
        AssertUtils.assertEquals(responseBody.getJob(), "Lead Engineer", "Job value mismatch");
    }

    @Test
    public void updateCompleteUserTest() {

        UserRequest request = new UserRequest();
        request.setName("UpdatedName");
        Response response= UserService.updateUserRaw(2, request);
        UserResponse responseBody = response.as(UserResponse.class);
        Reporter.getCurrentTestResult().setAttribute("response", response);
        AssertUtils.assertEquals(responseBody.getName(), "UpdatedName", "Name mismatch");
    }

    @Test
    public void deleteUserTest() {
        Response response = UserService.deleteUserRaw(2);
        Reporter.getCurrentTestResult().setAttribute("response", response);
        response.then()
                .log().ifValidationFails()
                .statusCode(204);
    }


}
