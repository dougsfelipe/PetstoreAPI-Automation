package org.APITest.factory;
import com.github.javafaker.Faker;
import org.APITest.model.UserDTO;

public class UserFactory {

    public static UserDTO createUser() {
        Faker faker = new Faker();
        UserDTO userDTO = new UserDTO();

        userDTO.setUsername(faker.name().username().replace(".", ""));
        userDTO.setFirstName(faker.name().firstName());
        userDTO.setLastName(faker.name().lastName());
        userDTO.setEmail(faker.internet().emailAddress());
        userDTO.setPassword(faker.internet().password());
        userDTO.setPhone(faker.phoneNumber().cellPhone());
        userDTO.setUserStatus(faker.number().numberBetween(0, 2)); // exemplo de status

        return userDTO;
    }
}
