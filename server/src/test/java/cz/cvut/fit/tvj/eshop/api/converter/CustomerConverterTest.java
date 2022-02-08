package cz.cvut.fit.tvj.eshop.api.converter;

import cz.cvut.fit.tvj.eshop.api.dto.CustomerDto;
import cz.cvut.fit.tvj.eshop.domain.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CustomerConverterTest {

    Customer customer = new Customer(1L, "customer1");
    CustomerDto customerDto = new CustomerDto(1L, "customer1");

    @Test
    void toModel() {
        Assertions.assertEquals(customer, CustomerConverter.toModel(customerDto));
    }

    @Test
    void fromModel() {
        Assertions.assertEquals(customerDto, CustomerConverter.fromModel(customer));
    }

    @Test
    void toModels() {
        Assertions.assertArrayEquals(new Customer[]{customer}, CustomerConverter.toModels(Arrays.stream((new CustomerDto[]{customerDto})).toList()).toArray());
    }

    @Test
    void fromModels() {
        Assertions.assertArrayEquals(new CustomerDto[]{customerDto}, CustomerConverter.fromModels(Arrays.stream((new Customer[]{customer})).toList()).toArray());
    }
}