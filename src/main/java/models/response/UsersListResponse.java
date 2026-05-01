package models.response;

import java.util.List;

public class UsersListResponse {

    private List<UserResponse> data;

    public List<UserResponse> getData() {
        return data;
    }

    public void setData(List<UserResponse> data) {
        this.data = data;
    }
}
