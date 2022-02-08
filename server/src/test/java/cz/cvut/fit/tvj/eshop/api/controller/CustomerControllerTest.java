package cz.cvut.fit.tvj.eshop.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fit.tvj.eshop.business.CustomerService;
import cz.cvut.fit.tvj.eshop.domain.Customer;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CustomerService service;

    @Test
    void all() throws Exception {
        Customer customer1 = new Customer(1L, "customer1");
        Customer customer2 = new Customer(2L, "customer2");
        var allCustomers = List.of(customer1, customer2);
        Mockito.when(service.readAll()).thenReturn(allCustomers);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username", Matchers.is("customer1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].username", Matchers.is("customer2")));
    }

    @Test
    void one() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/1").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void create() throws Exception {
        Customer customer = new Customer(1L, "customer1");
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(customer)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).create(customer);
    }

    @Test
    void update() throws Exception {
        Customer customer = new Customer(1L, "customer1");
        Mockito.when(service.readById(1L)).thenReturn(Optional.of(customer));
        mockMvc.perform(MockMvcRequestBuilders.put("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(customer)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.aMapWithSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[\"id\"]", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[\"username\"]", Matchers.is("customer1")));
        Mockito.verify(service, Mockito.times(1)).update(customer);
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).deleteById(1L);
    }
}