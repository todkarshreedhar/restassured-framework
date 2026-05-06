package utils;

import models.request.UserRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class UserFactory {
    private static final Logger log =
            LogManager.getLogger(UserFactory.class);
    private UserFactory() {
        // Prevent object creation
    }
    // 🔹 Dynamic random user
    public static UserRequest createRandomUser() {

        String uniqueId = UUID.randomUUID()
                .toString()
                .substring(0, 8);

        UserRequest request = new UserRequest();

        request.setName("User_" + uniqueId);
        request.setJob("AutomationEngineer_" + uniqueId);
        log.info("Generated random user: {}", request.getName());

        return request;
    }

    // 🔹 Stable reusable user
    public static UserRequest createDefaultUser() {

        return new UserRequest(
                "Shreedhar",
                "Automation Engineer"
        );
    }

    // 🔹 Invalid user
    public static UserRequest createInvalidUser() {

        return new UserRequest(
                "",
                ""
        );
    }

    // 🔹 Partial update user
    public static UserRequest createPartialUpdateUser() {

        UserRequest request = new UserRequest();

        request.setJob("Lead Engineer");

        return request;
    }

    // 🔹 Custom user
    public static UserRequest createUser(String name, String job) {

        return new UserRequest(name, job);
    }
}
