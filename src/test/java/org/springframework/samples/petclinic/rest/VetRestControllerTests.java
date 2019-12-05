/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.model.Vet;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

/**
 * Test class for {@link VetRestController}
 *
 * @author Vitaliy Fedoriv
 */
@QuarkusTest
public class VetRestControllerTests extends TestBase {

    private static List<Vet> vets;

    @BeforeAll
    public static void initVets(){
    	vets = new ArrayList<Vet>();


    	Vet vet = new Vet();
    	vet.setId(1);
    	vet.setFirstName("James");
    	vet.setLastName("Carter");
    	vets.add(vet);

    	vet = new Vet();
    	vet.setId(2);
    	vet.setFirstName("Helen");
    	vet.setLastName("Leary");
    	vets.add(vet);

    	vet = new Vet();
    	vet.setId(3);
    	vet.setFirstName("Linda");
    	vet.setLastName("Douglas");
    	vets.add(vet);
    }

    @Test
    public void testGetVetSuccess() throws Exception {
        retrievalRequestSpec()
            .when().get("/api/vets/1")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType("application/json;charset=UTF-8")
            .body("$.size()", is(1),
                "id", is(1),
                "firstName", is("James"));
    }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testGetVetNotFound() throws Exception {
        retrievalRequestSpec()
            .when().get("/api/vets/-1")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());

    }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testGetAllVetsSuccess() throws Exception {
        retrievalRequestSpec()
            .when().get("/api/vets/")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType("application/json;charset=UTF-8")
            .body("[0].id", is(2),
                "[0].firstName", is("James"),
                "[1].id", is(3),
                "[1].firstName", is("Helen"));
    }

//    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testGetAllVetsNotFound() throws Exception {
//    	vets.clear();
//    	given(this.clinicService.findAllVets()).willReturn(vets);
//        this.mockMvc.perform(get("/api/vets/")
//        	.accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNotFound());
    }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testCreateVetSuccess() throws Exception {
    	Vet newVet = vets.get(0);
    	newVet.setId(999);
    	ObjectMapper mapper = new ObjectMapper();
    	String newVetAsJSON = mapper.writeValueAsString(newVet);

        modificationRequestSpec()
            .body(newVetAsJSON)
            .when().post("/api/vets/")
            .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testCreateVetError() throws Exception {
    	Vet newVet = vets.get(0);
    	newVet.setId(null);
    	newVet.setFirstName(null);
    	ObjectMapper mapper = new ObjectMapper();
    	String newVetAsJSON = mapper.writeValueAsString(newVet);

        modificationRequestSpec()
            .body(newVetAsJSON)
            .when().post("/api/vets/")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
     }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testUpdateVetSuccess() throws Exception {
//    	given(this.clinicService.findVetById(1)).willReturn(vets.get(0));
    	Vet newVet = vets.get(0);
    	newVet.setFirstName("James");
    	ObjectMapper mapper = new ObjectMapper();
    	String newVetAsJSON = mapper.writeValueAsString(newVet);
        modificationRequestSpec()
            .body(newVetAsJSON)
            .when().put("/api/vets/1")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value())
            .contentType("application/json;charset=UTF-8");

        retrievalRequestSpec()
            .when().get("/api/vets/1")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType("application/json;charset=UTF-8")
            .body("id", is(1),
                "name", is("James"));
    }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testUpdateVetError() throws Exception {
    	Vet newVet = vets.get(0);
    	newVet.setFirstName("");
    	ObjectMapper mapper = new ObjectMapper();
    	String newVetAsJSON = mapper.writeValueAsString(newVet);

        modificationRequestSpec()
            .body(newVetAsJSON)
            .when().put("/api/vets/1")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
     }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testDeleteVetSuccess() throws Exception {
    	Vet newVet = vets.get(0);
    	ObjectMapper mapper = new ObjectMapper();
    	String newVetAsJSON = mapper.writeValueAsString(newVet);
        modificationRequestSpec()
            .body(newVetAsJSON)
            .when().delete("/api/vets/1")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());

    }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testDeleteVetError() throws Exception {
    	Vet newVet = vets.get(0);
    	ObjectMapper mapper = new ObjectMapper();
    	String newVetAsJSON = mapper.writeValueAsString(newVet);
        modificationRequestSpec()
            .body(newVetAsJSON)
            .when().delete("/api/vets/-1")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}

