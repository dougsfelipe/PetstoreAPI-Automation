package org.APITest.controller;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.APITest.model.PetDTO;
import org.APITest.util.Endpoint;

import static org.hamcrest.CoreMatchers.is;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Pet {


    public static long register_pet(PetDTO pet, Integer statusCode, String environment) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post(environment.concat(Endpoint.pets)) // Ex: "/v2/pet"
                .then()
                .statusCode(statusCode)
                .body("status", equalTo(pet.getStatus()))
                .body("name", equalTo(pet.getName()))
                .log().all()
                .extract().response();

        long id = Long.valueOf(response.path("id").toString());
        return id;
    }

    public static void search_pet_by_id(PetDTO pet, Integer statusCode, String environment) {
        given()
                .pathParam("petId", pet.getId())
                .contentType(ContentType.JSON)
                .when()
                .get(environment.concat(Endpoint.get_pets))
                .then()
                .statusCode(statusCode)
                .body("id", equalTo(pet.getId().intValue()))
                .body("name", is(pet.getName()))
                .body("status", is(pet.getStatus()))
                .log().all()
                .extract().response();

    }

    public static void test_update_pet_by_id_using_post(PetDTO pet, Integer statusCode, String environment) {
       given()
               .contentType(ContentType.URLENC) // application/x-www-form-urlencoded
               .accept(ContentType.JSON)
               .pathParam("petId", pet.getId())
               .queryParam("name", pet.getName())
               .queryParam("status", pet.getStatus())
               .log().all()
               .when()
               .post(environment.concat(Endpoint.get_pets))
               .then()
               .statusCode(statusCode);

    }

}