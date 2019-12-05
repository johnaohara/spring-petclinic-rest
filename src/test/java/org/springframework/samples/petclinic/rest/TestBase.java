package org.springframework.samples.petclinic.rest;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;

public abstract class TestBase {

    static  RequestSpecification retrievalRequestSpec(){
        return given()
            .auth().basic("admin", "adm1n")
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .when();
    }


    static RequestSpecification modificationRequestSpec(){
        return given()
            .auth().basic("admin", "adm1n")
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
    }


    static List<Pet> pets;
    static List<Owner> owners;

    @BeforeAll
    public static void initPets(){

        owners = new ArrayList<Owner>();

        Owner owner = new Owner();
        owner.setId(1);
        owner.setFirstName("George");
        owner.setLastName("Franklin");
        owner.setAddress("110 W. Liberty St.");
        owner.setCity("Madison");
        owner.setTelephone("6085551023");
        owners.add(owner);

        owner = new Owner();
        owner.setId(2);
        owner.setFirstName("Betty");
        owner.setLastName("Davis");
        owner.setAddress("638 Cardinal Ave.");
        owner.setCity("Sun Prairie");
        owner.setTelephone("6085551749");
        owners.add(owner);

        owner = new Owner();
        owner.setId(3);
        owner.setFirstName("Eduardo");
        owner.setLastName("Rodriquez");
        owner.setAddress("2693 Commerce St.");
        owner.setCity("McFarland");
        owner.setTelephone("6085558763");
        owners.add(owner);

        owner = new Owner();
        owner.setId(4);
        owner.setFirstName("Harold");
        owner.setLastName("Davis");
        owner.setAddress("563 Friendly St.");
        owner.setCity("Windsor");
        owner.setTelephone("6085553198");
        owners.add(owner);



        pets = new ArrayList<Pet>();

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

}
