package org.springframework.samples.petclinic.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.model.User;

@QuarkusTest
public class UserRestControllerTests extends TestBase {

    @Test
    public void testCreateUserSuccess() throws Exception {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(true);
        user.addRole( "OWNER_ADMIN" );
        ObjectMapper mapper = new ObjectMapper();
        String newUserAsJson = mapper.writeValueAsString(user);

        modificationRequestSpec()
            .body(newUserAsJson)
            .when().put("/api/users/")
            .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void testCreateUserError() throws Exception {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(true);
        ObjectMapper mapper = new ObjectMapper();
        String newUserAsJSON = mapper.writeValueAsString(user);

        modificationRequestSpec()
            .body(newUserAsJSON)
            .when().put("/api/users/")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
