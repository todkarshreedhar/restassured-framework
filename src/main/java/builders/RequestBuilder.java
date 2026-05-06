package builders;

import models.request.UserRequest;

public class RequestBuilder {
    private RequestBuilder() {
        // Prevent object creation
    }
    // 🔹 Entry point
    public static UserBuilder user() {
        return new UserBuilder();
    }

    // 🔹 User Builder
    public static class UserBuilder {

        private final UserRequest request;

        public UserBuilder() {
            this.request = new UserRequest();
        }

        public UserBuilder name(String name) {
            request.setName(name);
            return this;
        }

        public UserBuilder job(String job) {
            request.setJob(job);
            return this;
        }

        public UserRequest build() {
            return request;
        }
    }
}
