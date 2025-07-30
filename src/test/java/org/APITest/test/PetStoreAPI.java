package org.APITest.test;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.APITest.controller.User;
import org.APITest.factory.UserFactory;
import org.APITest.model.UserDTO;
import org.APITest.util.Message;
import org.APITest.util.RetryExtension;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.APITest.controller.User.register_user;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetStoreAPI {

    private static int bookId;
    private static final Faker faker = new Faker();

    private static String environment;

    @BeforeAll
    public static void definirAmbiente() {
        environment = "https://petstore.swagger.io/v2";
        RestAssured.baseURI = environment;
    }


    @Test
    @Order(1)
    @DisplayName("Create a user with Faker API")
    void test_create_user() {
        UserDTO userDTO = UserFactory.createUser();
        userDTO.setId(register_user(userDTO, HttpStatus.SC_OK, Message.post_book_sucess, environment));

    }

    @ExtendWith(RetryExtension.class)
    @Test
    @Order(2)
    @DisplayName("Search a User by ID")
    void test_search_user() {
        UserDTO userDTO = UserFactory.createUser();
        userDTO.setId(register_user(userDTO, HttpStatus.SC_OK, Message.post_book_sucess, environment));
        System.out.println("User criado com ID: " + userDTO.getId());
        User.test_search_user(userDTO, HttpStatus.SC_OK, environment);



    }

}
