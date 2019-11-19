package org.springframework.samples.petclinic.rest;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class UserRestControllerTests {

//    @Test
//    @WithMockUser(roles="ADMIN")
    public void testCreateUserSuccess() throws Exception {
//        User user = new User();
//        user.setUsername("username");
//        user.setPassword("password");
//        user.setEnabled(true);
//        user.addRole( "OWNER_ADMIN" );
//        ObjectMapper mapper = new ObjectMapper();
//        String newVetAsJSON = mapper.writeValueAsString(user);
//        this.mockMvc.perform(post("/api/users/")
//            .content(newVetAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(status().isCreated());
    }

//    @Test
//    @WithMockUser(roles="ADMIN")
    public void testCreateUserError() throws Exception {
//        User user = new User();
//        user.setUsername("username");
//        user.setPassword("password");
//        user.setEnabled(true);
//        ObjectMapper mapper = new ObjectMapper();
//        String newVetAsJSON = mapper.writeValueAsString(user);
//        this.mockMvc.perform(post("/api/users/")
//            .content(newVetAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(status().isBadRequest());
    }
}
