package cz.cvut.fit.tvj.eshop.api.converter;

import cz.cvut.fit.tvj.eshop.api.dto.CustomerDto;
import cz.cvut.fit.tvj.eshop.api.dto.OrderDto;
import cz.cvut.fit.tvj.eshop.domain.Customer;
import cz.cvut.fit.tvj.eshop.domain.Order;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class OrderConverterTest {

    Order order = new Order(1L, new Customer(), LocalDateTime.now(), Collections.emptySet());
    OrderDto orderDto = new OrderDto(1L, new CustomerDto(), LocalDateTime.now(), Collections.emptySet());

    @Test
    void toModel() {
        Assertions.assertEquals(order, OrderConverter.toModel(orderDto));
    }

    @Test
    void fromModel() {
        Assertions.assertEquals(orderDto, OrderConverter.fromModel(order));
    }

    @Test
    void toModels() {
        Assertions.assertArrayEquals(new Order[]{order}, OrderConverter.toModels(Arrays.stream((new OrderDto[]{orderDto})).toList()).toArray());
    }

    @Test
    void fromModels() {
        Assertions.assertArrayEquals(new OrderDto[]{orderDto}, OrderConverter.fromModels(Arrays.stream((new Order[]{order})).toList()).toArray());
    }
}