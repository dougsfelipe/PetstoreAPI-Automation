package org.APITest.factory;
import com.github.javafaker.Faker;
import org.APITest.model.CategoryDTO;
import org.APITest.model.PetDTO;
import org.APITest.model.TagDTO;
import org.APITest.model.UserDTO;

import java.util.List;

public class PetFactory {

    public static PetDTO createPet() {
        Faker faker = new Faker();
        PetDTO petDTO = new PetDTO();

        petDTO.setId(faker.number().randomNumber());

        CategoryDTO category = new CategoryDTO();
        category.setId((long) faker.number().numberBetween(1, 100));
        category.setName(faker.animal().name());
        petDTO.setCategory(category);

        petDTO.setName(faker.dog().name());

        petDTO.setPhotoUrls(List.of(faker.internet().url()));

        TagDTO tag = new TagDTO();
        tag.setId((long) faker.number().numberBetween(1, 100));
        tag.setName(faker.color().name());
        petDTO.setTags(List.of(tag));

        petDTO.setStatus("available");

        return petDTO;
    }
}
