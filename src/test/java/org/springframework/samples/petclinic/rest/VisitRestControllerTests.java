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
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Visit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

/**
 * Test class for {@link VisitRestController}
 *
 * @author Vitaliy Fedoriv
 */
@QuarkusTest
public class VisitRestControllerTests extends TestBase {

    private static List<Visit> visits;

    @BeforeAll
    public static void initVisits(){
    	visits = new ArrayList<Visit>();

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
    	pet.setId(8);
    	pet.setName("Rosy");
    	pet.setBirthDate(new Date());
    	pet.setOwner(owner);
    	pet.setType(petType);


    	Visit visit = new Visit();
    	visit.setId(2);
    	visit.setPet(pet);
    	visit.setDate(new Date());
    	visit.setDescription("rabies shot");
    	visits.add(visit);

    	visit = new Visit();
    	visit.setId(3);
    	visit.setPet(pet);
    	visit.setDate(new Date());
    	visit.setDescription("neutered");
    	visits.add(visit);
    }

    @Test
//    @WithMockUser(roles="OWNER_ADMIN")
    public void testGetVisitSuccess() throws Exception {
        retrievalRequestSpec()
            .when().get("/api/visits/2")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType("application/json;charset=UTF-8")
            .body("id", is(2),
                "description", is("rabies shot"));
    }

    @Test
//    @WithMockUser(roles="OWNER_ADMIN")
    public void testGetVisitNotFound() throws Exception {
        retrievalRequestSpec()
            .when().get("/api/visits/-1")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
//    @WithMockUser(roles="OWNER_ADMIN")
    public void testGetAllVisitsSuccess() throws Exception {
        retrievalRequestSpec()
            .when().get("/api/visits/")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("[0].id", is(1),
                "[0].description", is("rabies shot"),
                "[1].id", is(3),
                "[1].description", is("neutered"));;

    }

//    @Test
//    @WithMockUser(roles="OWNER_ADMIN")
    public void testGetAllVisitsNotFound() throws Exception {
//    	visits.clear();
//    	given(this.clinicService.findAllVisits()).willReturn(visits);
//        this.mockMvc.perform(get("/api/visits/")
//        	.accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNotFound());
    }

    @Test
//    @WithMockUser(roles="OWNER_ADMIN")
    public void testCreateVisitSuccess() throws Exception {
    	Visit newVisit = visits.get(0);
    	newVisit.setId(999);
    	ObjectMapper mapper = new ObjectMapper();
    	String newVisitAsJSON = mapper.writeValueAsString(newVisit);

        modificationRequestSpec()
            .body(newVisitAsJSON)
            .when().post("/api/visits/")
            .then()
            .statusCode(HttpStatus.CREATED.value());

//    	this.mockMvc.perform(post("/api/visits/")
//    		.content(newVisitAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
//    		.andExpect(status().isCreated());
    }

    @Test
//    @WithMockUser(roles="OWNER_ADMIN")
    public void testCreateVisitError() throws Exception {
    	Visit newVisit = visits.get(0);
    	newVisit.setDescription(null);
    	ObjectMapper mapper = new ObjectMapper();
    	String newVisitAsJSON = mapper.writeValueAsString(newVisit);

        modificationRequestSpec()
            .body(newVisitAsJSON)
            .when().post("/api/visits/")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
     }

    @Test
//    @WithMockUser(roles="OWNER_ADMIN")
    public void testUpdateVisitSuccess() throws Exception {
//    	given(this.clinicService.findVisitById(2)).willReturn(visits.get(0));
    	Visit newVisit = visits.get(0);
    	newVisit.setDescription("rabies shot test");
    	ObjectMapper mapper = new ObjectMapper();
    	String newVisitAsJSON = mapper.writeValueAsString(newVisit);

        modificationRequestSpec()
            .body(newVisitAsJSON)
            .when().put("/api/visits/2")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value())
            .contentType("application/json;charset=UTF-8");

        retrievalRequestSpec()
            .when().get("/api/visits/2")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType("application/json;charset=UTF-8")
            .body("id", is(2),
                "description", is("rabies shot test"));
    }

    @Test()
//    @WithMockUser(roles="OWNER_ADMIN")
    public void testUpdateVisitError() throws Exception {
    	Visit newVisit = visits.get(0);
//    	newVisit.setPet(null);
        newVisit.setDescription(null);
    	ObjectMapper mapper = new ObjectMapper();
    	String newVisitAsJSON = mapper.writeValueAsString(newVisit);
        modificationRequestSpec()
            .body(newVisitAsJSON)
            .when().put("/api/visits/2")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
     }

    @Test
//    @WithMockUser(roles="OWNER_ADMIN")
    public void testDeleteVisitSuccess() throws Exception {
    	Visit newVisit = visits.get(0);
    	ObjectMapper mapper = new ObjectMapper();
    	String newVisitAsJSON = mapper.writeValueAsString(newVisit);

        modificationRequestSpec()
            .body(newVisitAsJSON)
            .when().delete("/api/visits/2")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
//    @WithMockUser(roles="OWNER_ADMIN")
    public void testDeleteVisitError() throws Exception {
    	Visit newVisit = visits.get(0);
    	ObjectMapper mapper = new ObjectMapper();
    	String newVisitAsJSON = mapper.writeValueAsString(newVisit);
        modificationRequestSpec()
            .body(newVisitAsJSON)
            .when().delete("/api/visits/-1")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
