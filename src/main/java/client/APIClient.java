package client;

import base.BaseRequest;


import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import static io.restassured.RestAssured.given;

public class APIClient {

    public static Response get(String endpoint) {
        return given()
                .spec(BaseRequest.getRequestSpec())
                .when()
                .get(endpoint);
    }

    public static Response post(String endpoint, Object body) {
        return given()
                .spec(BaseRequest.getRequestSpec())
                .body(body)
                .when()
                .post(endpoint);
    }

    public static Response put(String endpoint, Object body) {
        return given()
                .spec(BaseRequest.getRequestSpec())
                .body(body)
                .when()
                .put(endpoint);
    }
    public static Response patch(String endpoint, Object body) {
        return given()
                .spec(BaseRequest.getRequestSpec())
                .body(body)
                .when()
                .patch(endpoint);
    }

    public static Response delete(String endpoint) {
        return given()
                .spec(BaseRequest.getRequestSpec())
                .when()
                .delete(endpoint);
    }
}
