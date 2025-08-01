package org.APITest.controller;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.APITest.model.PetDTO;
import org.APITest.model.UserDTO;
import org.APITest.util.Endpoint;

import java.io.File;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

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



    public static void test_search_pet_by_status(String status, Integer statusCode, String environment) {
        given()
                .contentType(ContentType.URLENC) // application/x-www-form-urlencoded
                .accept(ContentType.JSON)
                .queryParam("status", status)
                .log().all()
                .when()
                .get(environment.concat(Endpoint.pet_status))
                .then()
                .statusCode(statusCode)
                .body("findAll { it.status != null }.status", everyItem(equalTo(status)));


    }

    public static void test_invalid_status(String endpointInvalido, Integer statusCode, String environment) {
        given()
                .accept(ContentType.JSON)
                .log().all()
                .when()
                .get(environment.concat(endpointInvalido))
                .then()
                .log().all()
                .statusCode(statusCode)
                .body(equalTo("{\"code\":404,\"message\":\"HTTP 404 Not Found\"}")); // valida corpo vazio
    }

    public static void test_search_pet_by_tags(String tags, Integer statusCodeEsperado, String environment) {
        given()
                .contentType(ContentType.URLENC)
                .accept(ContentType.JSON)
                .queryParam("tags", tags)
                .log().all()
                .when()
                .get(environment.concat(Endpoint.pets_tags))
                .then()
                .log().all()
                .statusCode(statusCodeEsperado)
                .body("tags.name.flatten()", hasItem(tags));
    }

    public static void test_delete_pet(PetDTO pet, Integer statusCode, String environment){
        given()
                .log().all()
                .pathParam("petId", pet.getId())
                .contentType(ContentType.JSON)
                .when()
                .delete(environment.concat(Endpoint.get_pets))
                .then()
                .statusCode(statusCode);
    }

    public static void test_search_pet_not_found(PetDTO pet, Integer statusCode, String ambiente){
        given()
                .pathParam("petId", pet.getId())
                .when()
                .get(ambiente.concat(Endpoint.get_pets))
                .then()
                .statusCode(statusCode);

    }

}