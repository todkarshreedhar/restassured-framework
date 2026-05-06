package utils;

import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class SchemaValidator {
    private SchemaValidator() {
        // Prevent object creation
    }

    public static void validate(Response response,
                                String schemaPath) {

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(schemaPath));
    }

}
