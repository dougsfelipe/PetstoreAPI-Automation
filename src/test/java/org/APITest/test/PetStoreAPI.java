package org.APITest.test;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.APITest.controller.Pet;
import org.APITest.controller.User;
import org.APITest.factory.OrderFactory;
import org.APITest.factory.PetFactory;
import org.APITest.factory.UserFactory;
import org.APITest.model.OrderDTO;
import org.APITest.model.PetDTO;
import org.APITest.model.UserDTO;
import org.APITest.util.Environment;
import org.APITest.util.Message;
import org.APITest.util.RetryExtension;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;

import static org.APITest.controller.Order.create_a_order;
import static org.APITest.controller.Pet.register_pet;
import static org.APITest.controller.User.register_user;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetStoreAPI {

    private static int bookId;
    private static final Faker faker = new Faker();

    private static String environment;

    @BeforeAll
    public static void definirAmbiente() {
        environment = Environment.localhost;
        RestAssured.baseURI = environment;
    }




    //Tests for Users
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
    @DisplayName("Search a User by Username")
    void test_search_user() {
        UserDTO userDTO = UserFactory.createUser();
        userDTO.setId(register_user(userDTO, HttpStatus.SC_OK, Message.post_book_sucess, environment));
        System.out.println("User criado com ID: " + userDTO.getId());
        User.test_search_user(userDTO, HttpStatus.SC_OK, environment);

    }

    @ExtendWith(RetryExtension.class)
    @Test
    @Order(3)
    @DisplayName("Update User by Username")
    void test_upate_user() {
        UserDTO userDTO = UserFactory.createUser();
        userDTO.setId(register_user(userDTO, HttpStatus.SC_OK, Message.post_book_sucess, environment));
        userDTO.setFirstName(userDTO.getFirstName() + " Atualizado");
        User.test_update_user(userDTO, HttpStatus.SC_OK, environment);
        User.test_search_user(userDTO, HttpStatus.SC_OK, environment);

    }

    @ExtendWith(RetryExtension.class)
    @Test
    @Order(4)
    @DisplayName("Delete User by Username")
    void test_delete_user() {
        UserDTO userDTO = UserFactory.createUser();
        userDTO.setId(register_user(userDTO, HttpStatus.SC_OK, Message.post_book_sucess, environment));

        User.test_delete_user(userDTO, userDTO.getUsername(), HttpStatus.SC_OK, environment);
        User.test_search_user_not_found(userDTO, userDTO.getUsername(), HttpStatus.SC_NOT_FOUND, environment, Message.user_not_found);

    }

    @ExtendWith(RetryExtension.class)
    @Test
    @Order(5)
    @DisplayName("Search for a non existent user")
    void test_search_for_non_existent_user() {
        UserDTO userDTO = UserFactory.createUser();
        User.test_search_user_not_found(userDTO, userDTO.getUsername() + "non_existent", HttpStatus.SC_NOT_FOUND, environment, Message.user_not_found);

    }

    @ExtendWith(RetryExtension.class)
    @Test
    @Order(6)
    @DisplayName("Login user")
    void test_login_user() {
        UserDTO userDTO = UserFactory.createUser();
        userDTO.setId(register_user(userDTO, HttpStatus.SC_OK, Message.post_book_sucess, environment));
        User.login_user_password(userDTO, HttpStatus.SC_OK, environment);


    }

    @ExtendWith(RetryExtension.class)
    @Test
    @Order(7)
    @DisplayName("Logout user")
    void test_logout_user() {
        UserDTO userDTO = UserFactory.createUser();
        userDTO.setId(register_user(userDTO, HttpStatus.SC_OK, Message.post_book_sucess, environment));
        User.login_user_password(userDTO, HttpStatus.SC_OK, environment);
        User.logout(HttpStatus.SC_OK, environment);

    }

    @ExtendWith(RetryExtension.class)
    @Test
    @Order(8)
    @DisplayName("Create a Pet")
    void test_create_pet() {
        PetDTO petDTO = PetFactory.createPet();
        petDTO.setId(register_pet(petDTO, HttpStatus.SC_OK, environment));
        System.out.println("Pet criado com ID: " + petDTO.getId());


    }

    @ExtendWith(RetryExtension.class)
    @Test
    @Order(9)
    @DisplayName("Search a Pet by ID")
    void test_search_pet_by_id() {
        PetDTO petDTO = PetFactory.createPet();
        petDTO.setId(register_pet(petDTO, HttpStatus.SC_OK, environment));
        Pet.search_pet_by_id(petDTO,HttpStatus.SC_OK, environment);

    }

    @ExtendWith(RetryExtension.class)
    @Test
    @Order(10)
    @DisplayName("Update a Pet by ID using POST")
    void test_update_pet_by_id_using_post() {
        PetDTO petDTO = PetFactory.createPet();
        petDTO.setId(register_pet(petDTO, HttpStatus.SC_OK, environment));
        petDTO.setName(petDTO.getName() + "_Att");
        petDTO.setStatus("pending");
        Pet.test_update_pet_by_id_using_post(petDTO,HttpStatus.SC_OK, environment);
        Pet.search_pet_by_id(petDTO,HttpStatus.SC_OK, environment);

    }

    @ExtendWith(RetryExtension.class)
    @Test
    @Order(10)
    @DisplayName("Update a Pet by ID using POST")
    void test_update_by_id_using_post() {
        PetDTO petDTO = PetFactory.createPet();
        petDTO.setId(register_pet(petDTO, HttpStatus.SC_OK, environment));
        petDTO.setName(petDTO.getName() + "_Att");
        petDTO.setStatus("pending");
        Pet.test_update_pet_by_id_using_post(petDTO,HttpStatus.SC_OK, environment);
        Pet.search_pet_by_id(petDTO,HttpStatus.SC_OK, environment);

    }

    @ExtendWith(RetryExtension.class)
    @Test
    @Order(11)
    @DisplayName("Finda Pet by Status")
    void test_find_by_status() {
        Pet.test_search_pet_by_status("available",  HttpStatus.SC_OK, environment);
        Pet.test_search_pet_by_status("pending",  HttpStatus.SC_OK, environment);
        Pet.test_search_pet_by_status("sold",  HttpStatus.SC_OK, environment);
        Pet.test_invalid_status("error", HttpStatus.SC_NOT_FOUND, environment);

    }

    @ExtendWith(RetryExtension.class)
    @Test
    @Order(12)
    @DisplayName("Find Pet by Tags")
    void test_find_by_tags() {
        Pet.test_search_pet_by_tags("purple", HttpStatus.SC_OK, environment);

    }

    @ExtendWith(RetryExtension.class)
    @Test
    @Order(13)
    @DisplayName("Delete a pet")
    void test_delete_a_pet() {
        PetDTO petDTO = PetFactory.createPet();
        petDTO.setId(register_pet(petDTO, HttpStatus.SC_OK, environment));
        Pet.test_delete_pet(petDTO, HttpStatus.SC_OK,environment);
        Pet.test_search_pet_not_found(petDTO, HttpStatus.SC_NOT_FOUND, environment);
    }


    @Test
    @Order(13)
    @DisplayName("Create a Order in Store")
    void test_create_a_order() {
        PetDTO petDTO = PetFactory.createPet();
        petDTO.setId(register_pet(petDTO, HttpStatus.SC_OK, environment));
        OrderDTO orderDTO = OrderFactory.createOrder(petDTO, 2 );
        orderDTO.setId(create_a_order(orderDTO, HttpStatus.SC_OK, environment));

    }



}
