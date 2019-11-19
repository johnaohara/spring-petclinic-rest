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
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;


/**
 * Test class for {@link PetRestController}
 *
 * @author Vitaliy Fedoriv
 */

@QuarkusTest
public class PetRestControllerTests {

//    @Autowired
//    private PetRestController petRestController;
//
//    @MockBean
//    protected ClinicService clinicService;
//
//    private MockMvc mockMvc;

    private static List<Pet> pets;

    @BeforeAll
    public static void initPets(){

    	pets = new ArrayList<Pet>();

    	Owner owner = new Owner();
    	owner.setId(1);
    	owner.setFirstName("Eduardo");
    	owner.setLastName("Rodriquez");
    	owner.setAddress("2693 Commerce St.");
    	owner.setCity("McFarland");
    	owner.setTelephone("6085558763");

    	PetType petType = new PetType();
    	petType.setId(2);
    	petType.setName("dog");

    	Pet pet = new Pet();
    	pet.setId(3);
    	pet.setName("Rosy");
    	pet.setBirthDate(new Date());
    	pet.setOwner(owner);
    	pet.setType(petType);
    	pets.add(pet);

    	pet = new Pet();
    	pet.setId(4);
    	pet.setName("Jewel");
    	pet.setBirthDate(new Date());
    	pet.setOwner(owner);
    	pet.setType(petType);
    	pets.add(pet);
    }

    @Test
    public void testGetPetSuccess() throws Exception {
        given()
            .auth().basic("admin", "adm1n")
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .when().get("/api/pets/3")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType("application/json;charset=UTF-8")
            .body("id", is(3),
                "name", is("Rosy"));
    }


    @Test
    //TODO:
    public void testGetPetNotFound() throws Exception {
        given()
            .auth().basic("admin", "adm1n")
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .when().get("/api/pets/-1")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testGetAllPetsSuccess() throws Exception {
        given()
            .auth().basic("admin", "adm1n")
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .when().get("/api/pets/")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType("application/json;charset=UTF-8")
            .body("[2].id", is(3),
                "[2].name", is("Rosy"),
                "[3].id", is(4),
                "[3].name", is("Jewel"));
    }

//    @Test
//    public void testGetAllPetsNotFound() throws Exception {
//    	pets.clear();
//    	given(this.clinicService.findAllPets()).willReturn(pets);
//        this.mockMvc.perform(get("/api/pets/")
//        	.accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNotFound());
//    }

    @Test
    public void testCreatePetSuccess() throws Exception {
    	Pet newPet = pets.get(0);
    	newPet.setId(999);
    	ObjectMapper mapper = new ObjectMapper();
    	String newPetAsJSON = mapper.writeValueAsString(newPet);

        given()
            .auth().basic("admin", "adm1n")
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .body(newPetAsJSON)
            .when().post("/api/pets/")
            .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void testCreatePetError() throws Exception {
    	Pet newPet = pets.get(0);
    	newPet.setId(null);
    	newPet.setName(null);
    	ObjectMapper mapper = new ObjectMapper();
    	String newPetAsJSON = mapper.writeValueAsString(newPet);

        given()
            .auth().basic("admin", "adm1n")
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .body(newPetAsJSON)
            .when().post("/api/pets/")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
     }

    @Test
    public void testUpdatePetSuccess() throws Exception {
    	Pet newPet = pets.get(0);
    	newPet.setName("Rosy I");
    	ObjectMapper mapper = new ObjectMapper();
    	String newPetAsJSON = mapper.writeValueAsString(newPet);

        given()
            .auth().basic("admin", "adm1n")
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .body(newPetAsJSON)
            .when().put("/api/pets/3")
            .then()
            .contentType("application/json;charset=UTF-8")
            .statusCode(HttpStatus.NO_CONTENT.value());

        given()
            .auth().basic("admin", "adm1n")
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .when().get("/api/pets/3")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType("application/json;charset=UTF-8")
            .body("id", is(3),
                "name", is("Rosy I"));

    }

    @Test
    public void testUpdatePetError() throws Exception {
    	Pet newPet = pets.get(0);
    	newPet.setName("");
    	ObjectMapper mapper = new ObjectMapper();
    	String newPetAsJSON = mapper.writeValueAsString(newPet);

        given()
            .auth().basic("admin", "adm1n")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .body(newPetAsJSON)
            .when().put("/api/pets/3")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());

     }

    @Test
    public void testDeletePetSuccess() throws Exception {
    	Pet newPet = pets.get(0);
    	ObjectMapper mapper = new ObjectMapper();
    	String newPetAsJSON = mapper.writeValueAsString(newPet);

        given()
            .auth().basic("admin", "adm1n")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .body(newPetAsJSON)
            .when().delete("/api/pets/3")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());

    }

    @Test
//    @WithMockUser(roles="OWNER_ADMIN")
    public void testDeletePetError() throws Exception {
    	Pet newPet = pets.get(0);
    	ObjectMapper mapper = new ObjectMapper();
    	String newPetAsJSON = mapper.writeValueAsString(newPet);

        given()
            .auth().basic("admin", "adm1n")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .body(newPetAsJSON)
            .when().delete("/api/pets/-1")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
