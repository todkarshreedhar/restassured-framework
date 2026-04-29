package client;

import base.BaseRequest;


import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import static io.restassured.RestAssured.given;

public class APIClient {

    public static Response sendRequest(String method, String endpoint, Object body) throws IllegalArgumentException,NullPointerException
    {

        RequestSpecification request = given().spec(BaseRequest.getRequestSpec());
        switch (method.toUpperCase()) {
            case "GET":
                return request
                        .when().get(endpoint)
                        .then().extract().response();

            case "POST":
                return request
                        .body(body)
                        .when().post(endpoint)
                        .then().extract().response();

            case "PUT":
                return request
                        .body(body)
                        .when().put(endpoint)
                        .then().extract().response();

            case "PATCH":
                return request
                        .body(body)
                        .when().patch(endpoint)
                        .then().extract().response();

            case "DELETE":
                return request
                        .when().delete(endpoint)
                        .then().extract().response();

            default:
                throw new IllegalArgumentException("Invalid HTTP method");
        }
    }
}
