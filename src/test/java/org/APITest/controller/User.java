package org.APITest.controller;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.APITest.model.UserDTO;
import org.APITest.util.Endpoint;

import static org.hamcrest.CoreMatchers.is;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class User {

    public static String register_user(UserDTO user, Integer statusCode, String message, String environment) {
        Response response = given()
                .body("{\n" +
                        "  \"username\": \"" + user.getUsername() + "\",\n" +
                        "  \"firstName\": \"" + user.getFirstName() + "\",\n" +
                        "  \"lastName\": \"" + user.getLastName() + "\",\n" +
                        "  \"email\": \"" + user.getEmail() + "\",\n" +
                        "  \"password\": \"" + user.getPassword() + "\",\n" +
                        "  \"phone\": \"" + user.getPhone() + "\",\n" +
                        "  \"userStatus\": " + user.getUserStatus() + "\n" +
                        "}")
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .post(environment.concat(Endpoint.user)) // Ex: /api/v1/Books
                .then()
                .statusCode(statusCode)
                .extract().response(); // Ou "_id", depende da resposta JSON real

        String id = response.path("message").toString();
        return id;
    }

    public static void test_search_user(UserDTO user, Integer statusCode, String ambiente){
        given()
                .pathParam("username", user.getUsername())
                .when()
                .get(ambiente.concat(Endpoint.get_username))
                .then()
                .statusCode(statusCode)
                .body("username", is(user.getUsername()))
                .body("firstName", is(user.getFirstName()))
                .body("lastName", is(user.getLastName()))
                .body("email", is(user.getEmail()))
                .body("password", is(user.getPassword()))
                .body("phone", is(user.getPhone()))
                .body("userStatus", is(user.getUserStatus()));

    }


}