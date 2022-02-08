package cz.cvut.fit.tvj.eshop.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fit.tvj.eshop.business.CustomerService;
import cz.cvut.fit.tvj.eshop.business.OrderService;
import cz.cvut.fit.tvj.eshop.domain.Customer;
import cz.cvut.fit.tvj.eshop.domain.Order;
import cz.cvut.fit.tvj.eshop.domain.Product;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private OrderService service;

    private final Customer customer = new Customer(1L, "customer1");
    private final Order order1 = new Order(1L, customer, LocalDateTime.now(), Set.of(new Product(1L, "product1", 1.), new Product(2L, "product2", 2.)));
    private final Order order2 = new Order(2L, new Customer(), LocalDateTime.now(), new HashSet<>());

    @Test
    void all() throws Exception {
        var allOrders = List.of(order1, order2);
        Mockito.when(service.readAll()).thenReturn(allOrders);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].customer.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].customer.username", Matchers.is("customer1")));
    }

    @Test
    void allByCustomer() throws Exception {
        Order order3 = new Order(2L, customer, LocalDateTime.now(), new HashSet<>());
        var allOrdersByCustomer = List.of(order1, order3);
        Mockito.when(service.readAllByCustomerId(1L)).thenReturn(allOrdersByCustomer);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/1/orders").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].customer.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].customer.username", Matchers.is("customer1")));
    }

    @Test
    void one() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/1").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void create() throws Exception {
        Order order = new Order(1L, customer, LocalDateTime.now(), Collections.emptySet());
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(order)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).create(order);
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void addProduct() throws Exception {
        Product product = new Product(1L, "product1", 1000.);

        Mockito.when(service.readById(2L)).thenReturn(Optional.of(order2));

        mockMvc.perform(MockMvcRequestBuilders.post("/orders/2/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(product)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertArrayEquals(new Product[]{product}, order2.getProducts().toArray()); // Unit test should not test this
        Mockito.verify(service, Mockito.times(1)).update(order2);
    }

    @Test
    void removeProduct() throws Exception {
        Mockito.when(service.readById(2L)).thenReturn(Optional.of(order2));

        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/2/products/5"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service, Mockito.times(1)).update(order2);
    }

}