package services;

import client.APIClient;
import constants.Endpoints;
import io.restassured.response.Response;
import models.request.UserRequest;
import models.response.UserResponse;
import models.response.UsersListResponse;
import models.response.SingleUserResponse;

import java.util.List;

public class UserService {

    // 🔹 CREATE
    public static Response createUserRaw(UserRequest request) {
        return APIClient.post(Endpoints.CREATE_USER, request);
    }

    public static UserResponse createUser(UserRequest request) {
        return createUserRaw(request).as(UserResponse.class);
    }

    // 🔹 GET ALL USERS
    public static Response getAllUsersRaw() {
        return APIClient.get(Endpoints.GET_USERS);
    }

    public static List<UserResponse> getAllUsers() {
        return getAllUsersRaw()
                .as(UsersListResponse.class)
                .getData();
    }

    // 🔹 GET SINGLE USER
    public static Response getUserRaw(int id) {
        return APIClient.get(Endpoints.BASE_USER_URL + "/" + id);
    }

    public static UserResponse getUser(int id) {
        return getUserRaw(id)
                .as(SingleUserResponse.class)
                .getData();
    }

    // 🔹 UPDATE (PUT)
    public static Response updateUserRaw(int id, UserRequest request) {
        return APIClient.put(Endpoints.BASE_USER_URL + "/" + id, request);
    }

    public static UserResponse updateUser(int id, UserRequest request) {
        return updateUserRaw(id, request).as(UserResponse.class);
    }

    // 🔹 UPDATE PARTIAL (PATCH)
    public static Response updatePartialUserRaw(int id, UserRequest request) {
        return APIClient.patch(Endpoints.BASE_USER_URL + "/" + id, request);
    }

    public static UserResponse updatePartialUser(int id, UserRequest request) {
        return updatePartialUserRaw(id, request).as(UserResponse.class);
    }

    // 🔹 DELETE
    public static Response deleteUserRaw(int id) {
        return APIClient.delete(Endpoints.BASE_USER_URL + "/" + id);
    }

    public static boolean deleteUser(int id) {
        return deleteUserRaw(id).statusCode() == 204;
    }
}