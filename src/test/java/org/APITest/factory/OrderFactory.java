package org.APITest.factory;

import com.github.javafaker.Faker;
import org.APITest.model.CategoryDTO;
import org.APITest.model.OrderDTO;
import org.APITest.model.PetDTO;
import org.APITest.model.TagDTO;

import java.time.OffsetDateTime;
import java.util.List;

public class OrderFactory {

    public static OrderDTO createOrder(PetDTO pet, Integer quantity) {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setPetId(pet.getId());
        orderDTO.setQuantity(quantity);
        orderDTO.setShipDate(OffsetDateTime.now().plusDays(1).toString());
        orderDTO.setStatus("approved");
        orderDTO.setComplete(true);

        return orderDTO;



    }
}
