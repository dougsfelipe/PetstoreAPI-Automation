package org.APITest.controller;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.APITest.model.UserDTO;
import org.APITest.util.Endpoint;
import org.hamcrest.CoreMatchers;

import static org.hamcrest.CoreMatchers.is;
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;



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

        String id = response.path("id").toString();
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

    public static void test_search_user_not_found(UserDTO user, String username, Integer statusCode, String ambiente, String messsage){
        given()
                .pathParam("username", username)
                .when()
                .get(ambiente.concat(Endpoint.get_username))
                .then()
                .statusCode(statusCode);


    }

    public static String test_update_user(UserDTO user, Integer statusCode, String environment){
        Response response = given()
                .body("{\n" +
                        "  \"username\": \"" + user.getUsername() + "\",\n" +
                        "  \"firstName\": \"" + user.getFirstName()  +"\",\n" +
                        "  \"lastName\": \"" + user.getLastName() + "\",\n" +
                        "  \"email\": \"" + user.getEmail() + "\",\n" +
                        "  \"password\": \"" + user.getPassword() + "\",\n" +
                        "  \"phone\": \"" + user.getPhone() + "\",\n" +
                        "  \"userStatus\": " + user.getUserStatus() + "\n" +
                        "}")
                .log().all()
                .pathParam("username", user.getUsername())
                .contentType(ContentType.JSON)
                .when()
                .put(environment.concat(Endpoint.get_username)) // Ex: /api/v1/Books
                .then()
                .statusCode(statusCode)
                .extract().response(); // Ou "_id", depende da resposta JSON real

        String id = response.path("id").toString();
        return id;
    }

    public static void test_delete_user(UserDTO user, String username,Integer statusCode, String environment){
        given()
                .log().all()
                .pathParam("username", user.getUsername())
                .contentType(ContentType.JSON)
                .when()
                .delete(environment.concat(Endpoint.get_username))
                .then()
                .statusCode(statusCode);



    }

    public static void login_user_password(UserDTO user, Integer statusCode, String environment) {
        given()
                .queryParam("username", user.getUsername())
                .queryParam("password", user.getPassword())
                .when()
                .get(environment.concat(Endpoint.login))
                .then()
                .statusCode(statusCode);
    }


    public static void logout(Integer statusCode, String environment){
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(environment.concat(Endpoint.logout)) // Ex: /api/v1/Books
                .then()
                .statusCode(statusCode);





    }


}