package tests;

import static org.hamcrest.Matchers.*;

import builders.RequestBuilder;
import io.restassured.response.Response;
import models.request.UserRequest;
import models.response.UserResponse;
import org.testng.Reporter;
import org.testng.annotations.Test;
import services.UserService;
import utils.AssertUtils;
import utils.SchemaValidator;
import utils.TestDataLoader;
import utils.UserFactory;

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
    public void createUserWithBuilderTest() {

        UserRequest request = RequestBuilder.user()
                .name("BuilderUser")
                .job("Automation Engineer")
                .build();

        Response response =
                UserService.createUserRaw(request);

        Reporter.getCurrentTestResult()
                .setAttribute("response", response);

        UserResponse responseBody =
                response.as(UserResponse.class);

        AssertUtils.assertStatusCode(
                response.getStatusCode(),
                201
        );

        AssertUtils.assertEquals(
                responseBody.getName(),
                request.getName(),
                "Name mismatch"
        );

        AssertUtils.assertEquals(
                responseBody.getJob(),
                request.getJob(),
                "Job mismatch"
        );
    }

    @Test
    public void createUserTest() {

        // Arrange
        UserRequest request =
                UserFactory.createRandomUser();

        // Act
        Response response =
                UserService.createUserRaw(request);

        Reporter.getCurrentTestResult()
                .setAttribute("response", response);

        UserResponse responseBody =
                response.as(UserResponse.class);

        // Assert
        AssertUtils.assertStatusCode(
                response.getStatusCode(),
                201
        );

        AssertUtils.assertEquals(
                responseBody.getName(),
                request.getName(),
                "Name mismatch"
        );

        AssertUtils.assertEquals(
                responseBody.getJob(),
                request.getJob(),
                "Job mismatch"
        );

        AssertUtils.assertNotNull(
                responseBody.getId(),
                "ID should not be null"
        );

        // 🔹 Schema validation
        SchemaValidator.validate(
                response,
                "schemas/createUserSchema.json"
        );
    }

    @Test
    public void updatePartialUserTest() {

        UserRequest request = TestDataLoader.load("testdata/users/partialUpdateUser.json", UserRequest.class);
        Response response = UserService.updatePartialUserRaw(3, request);
        UserResponse responseBody = response.as(UserResponse.class);
        Reporter.getCurrentTestResult().setAttribute("response", response);
        AssertUtils.assertEquals(responseBody.getJob(), "Lead Engineer", "Job value mismatch");
    }

    @Test
    public void updateCompleteUserTest() {

        UserRequest request = TestDataLoader.load("testdata/users/updateUser.json", UserRequest.class);
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
