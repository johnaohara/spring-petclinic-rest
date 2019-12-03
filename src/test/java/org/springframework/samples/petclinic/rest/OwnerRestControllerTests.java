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
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.model.Owner;

import static org.hamcrest.CoreMatchers.is;


/**
 * Test class for {@link OwnerRestController}
 *
 * @author Vitaliy Fedoriv
 */
@QuarkusTest
public class OwnerRestControllerTests extends TestBase{

    @Test
    public void testGetOwnerSuccess() throws Exception {
        getRequestSpec().get("/api/owners/1")
            .then()
            .statusCode(200)
            .contentType("application/json;charset=UTF-8")
            .body("firstName", is("George"));
    }

    @Test
    public void testGetOwnerNotFound() throws Exception {
        getRequestSpec().get("/api/owners/-1")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testGetOwnersListSuccess() throws Exception {
        getRequestSpec().get("/api/owners/*/lastname/Davis")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType("application/json;charset=UTF-8")
            .body(".[0].id", is(2),
            ".[0].firstName", is("Betty"),
                ".[1].id", is(4),
                ".[1].firstName", is("Harold")
            );
 }

    @Test
    public void testGetOwnersListNotFound() throws Exception {
        getRequestSpec().get("/api/owners/*/lastname/Smith")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());

        }

    @Test
    public void testGetAllOwnersSuccess() throws Exception {

        getRequestSpec().get("/api/owners/")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType("application/json;charset=UTF-8")
            .body(".[0].id", is(2),
                ".[0].firstName", is("Betty"),
                ".[1].id", is(4),
                ".[1].firstName", is("Harold")
            );;
        //    	owners.remove(0);
//    	owners.remove(1);
//    	given(this.clinicService.findAllOwners()).willReturn(owners);
//        this.mockMvc.perform(get("/api/owners/")
//        	.accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType("application/json;charset=UTF-8"))
//            .andExpect(jsonPath("$.[0].id").value(2))
//            .andExpect(jsonPath("$.[0].firstName").value("Betty"))
//            .andExpect(jsonPath("$.[1].id").value(4))
//            .andExpect(jsonPath("$.[1].firstName").value("Harold"));
    }

    @Test
    public void testGetAllOwnersNotFound() throws Exception {
//    	owners.clear();
//    	given(this.clinicService.findAllOwners()).willReturn(owners);
//        this.mockMvc.perform(get("/api/owners/")
//        	.accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateOwnerSuccess() throws Exception {
    	Owner newOwner = owners.get(0);
    	newOwner.setId(999);
    	ObjectMapper mapper = new ObjectMapper();
    	String newOwnerAsJSON = mapper.writeValueAsString(newOwner);

        postRequestSpec()
            .body(newOwnerAsJSON)
            .when().post("/api/owners/")
            .then()
            .statusCode(HttpStatus.CREATED.value());

    }

    @Test
    public void testCreateOwnerError() throws Exception {
        Owner newOwner = owners.get(0);
        newOwner.setId(null);
        newOwner.setFirstName(null);
        ObjectMapper mapper = new ObjectMapper();
        String newOwnerAsJSON = mapper.writeValueAsString(newOwner);

        postRequestSpec()
            .body(newOwnerAsJSON)
            .when().post("/api/owners/")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    public void testUpdateOwnerSuccess() throws Exception {
    	Owner newOwner = owners.get(0);
    	newOwner.setFirstName("George I");
    	ObjectMapper mapper = new ObjectMapper();
    	String newOwnerAsJSON = mapper.writeValueAsString(newOwner);

        postRequestSpec()
            .body(newOwnerAsJSON)
            .when().put("/api/owners/1")
            .then()
            .contentType("application/json;charset=UTF-8")
            .statusCode(HttpStatus.NO_CONTENT.value());

        getRequestSpec()
            .get("/api/owners/1")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType("application/json;charset=UTF-8")
            .body("id", is(1),
                "name", is("George I"));
    }

    @Test
    public void testUpdateOwnerError() throws Exception {
    	Owner newOwner = owners.get(0);
    	newOwner.setFirstName("");
    	ObjectMapper mapper = new ObjectMapper();
    	String newOwnerAsJSON = mapper.writeValueAsString(newOwner);
        postRequestSpec()
            .body(newOwnerAsJSON)
            .when().put("/api/owners/1")
            .then()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testDeleteOwnerSuccess() throws Exception {
    	Owner newOwner = owners.get(0);
    	ObjectMapper mapper = new ObjectMapper();
    	String newOwnerAsJSON = mapper.writeValueAsString(newOwner);
//    	given(this.clinicService.findOwnerById(1)).willReturn(owners.get(0));
        postRequestSpec()
            .body(newOwnerAsJSON)
            .when().delete("/api/owners/1")
            .then()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .statusCode(HttpStatus.NO_CONTENT.value());

    }

    @Test
    public void testDeleteOwnerError() throws Exception {
        postRequestSpec()
            .when().delete("/api/owners/1-")
            .then()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
