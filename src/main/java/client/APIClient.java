package client;

import base.BaseRequest;


import io.restassured.response.Response;
import constants.ServiceType;


import static io.restassured.RestAssured.given;

public class APIClient {

    public static Response get(ServiceType serviceType,String endpoint) {
        return given()
                .spec(BaseRequest.getRequestSpec(serviceType))
                .when()
                .get(endpoint);
    }

    public static Response post(ServiceType serviceType,String endpoint, Object body) {
        return given()
                .spec(BaseRequest.getRequestSpec(serviceType))
                .body(body)
                .when()
                .post(endpoint);
    }

    public static Response put(ServiceType serviceType,String endpoint, Object body) {
        return given()
                .spec(BaseRequest.getRequestSpec(serviceType))
                .body(body)
                .when()
                .put(endpoint);
    }
    public static Response patch(ServiceType serviceType,String endpoint, Object body) {
        return given()
                .spec(BaseRequest.getRequestSpec(serviceType))
                .body(body)
                .when()
                .patch(endpoint);
    }

    public static Response delete(ServiceType serviceType,String endpoint) {
        return given()
                .spec(BaseRequest.getRequestSpec(serviceType))
                .when()
                .delete(endpoint);
    }
}
