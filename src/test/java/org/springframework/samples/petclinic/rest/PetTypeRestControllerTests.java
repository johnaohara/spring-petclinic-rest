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
import org.springframework.samples.petclinic.model.PetType;

import javax.ws.rs.core.MediaType;

import static org.hamcrest.CoreMatchers.is;


/**
 * Test class for {@link PetTypeRestController}
 *
 * @author John O'Hara
 */
@QuarkusTest
public class PetTypeRestControllerTests extends TestBase {

    @Test
//    @WithMockUser(roles="OWNER_ADMIN")
    public void testGetPetTypeSuccessAsOwnerAdmin() throws Exception {
        retrievalRequestSpec().get("/api/pettypes/1").then()
            .statusCode(HttpStatus.OK.value())
            .contentType("application/json;charset=UTF-8")
            .body("id", is(1),
                "name", is("cat"));
    }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testGetPetTypeSuccessAsVetAdmin() throws Exception {
//        given(this.clinicService.findPetTypeById(1)).willReturn(petTypes.get(0));
//        this.mockMvc.perform(get("/api/pettypes/1")
//            .accept(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType("application/json;charset=UTF-8"))
//            .andExpect(jsonPath("$.id").value(1))
//            .andExpect(jsonPath("$.name").value("cat"));
    }

    @Test
//    @WithMockUser(roles="OWNER_ADMIN")
    public void testGetPetTypeNotFound() throws Exception {
        retrievalRequestSpec().get("/api/pettypes/-1").then()
            .statusCode(HttpStatus.NOT_FOUND.value());
//    	given(this.clinicService.findPetTypeById(-1)).willReturn(null);
    }

    @Test
//    @WithMockUser(roles="OWNER_ADMIN")
    public void testGetAllPetTypesSuccessAsOwnerAdmin() throws Exception {
//    	petTypes.remove(0);
//    	petTypes.remove(1);
        retrievalRequestSpec().get("/api/pettypes/").then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .contentType(MediaType.APPLICATION_JSON)
            .statusCode(HttpStatus.OK.value())
            .body("[1].id", is(2),
                "[1].name", is("dog"),
                "[3].id", is(4),
                "[3].name", is("snake"))
        ;
    }

//    @Test
//    @WithMockUser(roles="VET_ADMIN")
//    public void testGetAllPetTypesSuccessAsVetAdmin() throws Exception {
//        petTypes.remove(0);
//        petTypes.remove(1);
//        given(this.clinicService.findAllPetTypes()).willReturn(petTypes);
//        this.mockMvc.perform(get("/api/pettypes/")
//            .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType("application/json;charset=UTF-8"))
//            .andExpect(jsonPath("$.[0].id").value(2))
//            .andExpect(jsonPath("$.[0].name").value("dog"))
//            .andExpect(jsonPath("$.[1].id").value(4))
//            .andExpect(jsonPath("$.[1].name").value("snake"));
//    }

//    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testGetAllPetTypesNotFound() throws Exception {
//    	petTypes.clear();
//    	given(this.clinicService.findAllPetTypes()).willReturn(petTypes);
//        this.mockMvc.perform(get("/api/pettypes/")
//        	.accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNotFound());
    }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testCreatePetTypeSuccess() throws Exception {
    	PetType newPetType = petTypes.get(0);
    	newPetType.setId(999);
    	ObjectMapper mapper = new ObjectMapper();
    	String newPetTypeAsJSON = mapper.writeValueAsString(newPetType);
//    	this.mockMvc.perform(post("/api/pettypes/")
//    		.content(newPetTypeAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
//    		.andExpect(status().isCreated());
    }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testCreatePetTypeError() throws Exception {
    	PetType newPetType = petTypes.get(0);
    	newPetType.setId(null);
    	newPetType.setName(null);
    	ObjectMapper mapper = new ObjectMapper();
    	String newPetTypeAsJSON = mapper.writeValueAsString(newPetType);
//    	this.mockMvc.perform(post("/api/pettypes/")
//        		.content(newPetTypeAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
//        		.andExpect(status().isBadRequest());
     }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testUpdatePetTypeSuccess() throws Exception {
//    	given(this.clinicService.findPetTypeById(2)).willReturn(petTypes.get(1));
    	PetType newPetType = petTypes.get(1);
    	newPetType.setName("dog I");
    	ObjectMapper mapper = new ObjectMapper();
    	String newPetTypeAsJSON = mapper.writeValueAsString(newPetType);
//    	this.mockMvc.perform(put("/api/pettypes/2")
//    		.content(newPetTypeAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
//        	.andExpect(content().contentType("application/json;charset=UTF-8"))
//        	.andExpect(status().isNoContent());
//
//    	this.mockMvc.perform(get("/api/pettypes/2")
//           	.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType("application/json;charset=UTF-8"))
//            .andExpect(jsonPath("$.id").value(2))
//            .andExpect(jsonPath("$.name").value("dog I"));
    }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testUpdatePetTypeError() throws Exception {
    	PetType newPetType = petTypes.get(0);
    	newPetType.setName("");
    	ObjectMapper mapper = new ObjectMapper();
    	String newPetTypeAsJSON = mapper.writeValueAsString(newPetType);
//    	this.mockMvc.perform(put("/api/pettypes/1")
//    		.content(newPetTypeAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
//        	.andExpect(status().isBadRequest());
     }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testDeletePetTypeSuccess() throws Exception {
    	PetType newPetType = petTypes.get(0);
    	ObjectMapper mapper = new ObjectMapper();
    	String newPetTypeAsJSON = mapper.writeValueAsString(newPetType);
//    	given(this.clinicService.findPetTypeById(1)).willReturn(petTypes.get(0));
//    	this.mockMvc.perform(delete("/api/pettypes/1")
//    		.content(newPetTypeAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
//        	.andExpect(status().isNoContent());
    }

    @Test
//    @WithMockUser(roles="VET_ADMIN")
    public void testDeletePetTypeError() throws Exception {
    	PetType newPetType = petTypes.get(0);
    	ObjectMapper mapper = new ObjectMapper();
    	String newPetTypeAsJSON = mapper.writeValueAsString(newPetType);
//    	given(this.clinicService.findPetTypeById(-1)).willReturn(null);
//    	this.mockMvc.perform(delete("/api/pettypes/-1")
//    		.content(newPetTypeAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
//        	.andExpect(status().isNotFound());
    }

}
