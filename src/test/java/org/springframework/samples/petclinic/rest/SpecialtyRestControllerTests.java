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
import org.springframework.samples.petclinic.model.Specialty;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;

/**
 * Test class for {@link SpecialtyRestController}
 *
 * @author Vitaliy Fedoriv
 */
@QuarkusTest
public class SpecialtyRestControllerTests extends TestBase {
    private static List<Specialty> specialties;

    @BeforeAll
    public static void initSpecialtys(){
    	specialties = new ArrayList<Specialty>();

    	Specialty specialty = new Specialty();
    	specialty.setId(1);
    	specialty.setName("radiology");
    	specialties.add(specialty);

    	specialty = new Specialty();
    	specialty.setId(2);
    	specialty.setName("surgery");
    	specialties.add(specialty);

    	specialty = new Specialty();
    	specialty.setId(3);
    	specialty.setName("dentistry");
    	specialties.add(specialty);

    }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testGetSpecialtySuccess() throws Exception {
        retrievalRequestSpec()
            .get("/api/specialties/1").then()
            .statusCode(HttpStatus.OK.value())
            .contentType("application/json;charset=UTF-8")
            .body("id", is(1),
                "name", startsWith("radiology"));
    }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testGetSpecialtyNotFound() throws Exception {
        retrievalRequestSpec()
            .get("/api/specialties/1").then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testGetAllSpecialtysSuccess() throws Exception {
//    	specialties.remove(0);
        retrievalRequestSpec()
            .get("/api/specialties/").then()
            .statusCode(HttpStatus.OK.value())
            .contentType("application/json;charset=UTF-8")
            .body("[1].id", is(2),
                "[1].name", is("surgery"),
                "[2].id", is(3),
                "[2].name", is("dentistry"));

    }

//    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testGetAllSpecialtysNotFound() throws Exception {
//    	specialties.clear();
//    	given(this.clinicService.findAllSpecialties()).willReturn(specialties);
//        this.mockMvc.perform(get("/api/specialties/")
//        	.accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNotFound());
    }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testCreateSpecialtySuccess() throws Exception {
    	Specialty newSpecialty = specialties.get(0);
    	newSpecialty.setId(999);
    	ObjectMapper mapper = new ObjectMapper();
    	String newSpecialtyAsJSON = mapper.writeValueAsString(newSpecialty);

        modificationRequestSpec()
            .body(newSpecialtyAsJSON)
            .when().post("/api/specialties/")
            .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testCreateSpecialtyError() throws Exception {
    	Specialty newSpecialty = specialties.get(0);
    	newSpecialty.setId(null);
    	newSpecialty.setName(null);
    	ObjectMapper mapper = new ObjectMapper();
    	String newSpecialtyAsJSON = mapper.writeValueAsString(newSpecialty);

    	modificationRequestSpec()
            .body(newSpecialtyAsJSON)
            .when().post("/api/specialties/")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
     }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testUpdateSpecialtySuccess() throws Exception {
//    	given(this.clinicService.findSpecialtyById(2)).willReturn(specialties.get(1));
    	Specialty newSpecialty = specialties.get(1);
    	newSpecialty.setName("surgery I");
    	ObjectMapper mapper = new ObjectMapper();
    	String newSpecialtyAsJSON = mapper.writeValueAsString(newSpecialty);

        modificationRequestSpec()
            .body(newSpecialtyAsJSON)
            .when().put("/api/specialties/2")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());


        retrievalRequestSpec()
            .when().get("/api/specialties/2")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType("application/json;charset=UTF-8")
            .body("id", is(2),
                "name", is("surgery I"));

    }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testUpdateSpecialtyError() throws Exception {
    	Specialty newSpecialty = specialties.get(0);
    	newSpecialty.setName("");
    	ObjectMapper mapper = new ObjectMapper();
    	String newSpecialtyAsJSON = mapper.writeValueAsString(newSpecialty);

        modificationRequestSpec()
            .body(newSpecialtyAsJSON)
            .when().put("/api/specialties/1")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
     }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testDeleteSpecialtySuccess() throws Exception {
    	Specialty newSpecialty = specialties.get(0);
    	ObjectMapper mapper = new ObjectMapper();
    	String newSpecialtyAsJSON = mapper.writeValueAsString(newSpecialty);

        modificationRequestSpec()
            .body(newSpecialtyAsJSON)
            .when().delete("/api/specialties/5")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testDeleteSpecialtyError() throws Exception {
    	Specialty newSpecialty = specialties.get(0);
    	ObjectMapper mapper = new ObjectMapper();
    	String newSpecialtyAsJSON = mapper.writeValueAsString(newSpecialty);

        modificationRequestSpec()
            .body(newSpecialtyAsJSON)
            .when().delete("/api/specialties/-1")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
