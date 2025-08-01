package org.APITest.controller;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.APITest.model.OrderDTO;
import org.APITest.model.PetDTO;
import org.APITest.util.Endpoint;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

public class Order {

    public static long create_a_order(OrderDTO order, Integer statusCode, String environment) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(environment.concat(Endpoint.order)) // Ex: "/v2/pet"
                .then()
                .statusCode(statusCode)
                .body("petId", equalTo(order.getPetId().intValue()))
                .body("quantity", equalTo(order.getQuantity()))
                .body("status", equalTo(order.getStatus()))
                .log().all()
                .extract().response();

        long id = Long.valueOf(response.path("id").toString());
        return id;
    }
}
