package cz.cvut.fit.tvj.eshop.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fit.tvj.eshop.business.CustomerService;
import cz.cvut.fit.tvj.eshop.business.OrderService;
import cz.cvut.fit.tvj.eshop.business.ProductService;
import cz.cvut.fit.tvj.eshop.domain.Customer;
import cz.cvut.fit.tvj.eshop.domain.Product;
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

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductService service;

    @Test
    void all() throws Exception {
        Product product1 = new Product(1L, "product1", 1.);
        Product product2 = new Product(2L, "product2", 2.);
        var allProducts = List.of(product1, product2);
        Mockito.when(service.readAll()).thenReturn(allProducts);

        mockMvc.perform(MockMvcRequestBuilders.get("/products").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("product1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price", Matchers.is(1.)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("product2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].price", Matchers.is(2.)));
    }

    @Test
    void one() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/1").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void create() throws Exception {
        Product product = new Product(1L, "product1", 1000.);
        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(product)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).create(product);
    }

    @Test
    void update() throws Exception {
        Product product = new Product(1L, "product1", 1000.);
        Mockito.when(service.readById(1L)).thenReturn(Optional.of(product));
        mockMvc.perform(MockMvcRequestBuilders.put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(product)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.aMapWithSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[\"id\"]", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[\"name\"]", Matchers.is("product1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[\"price\"]", Matchers.is(1000.)));
        Mockito.verify(service, Mockito.times(1)).update(product);
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).deleteById(1L);
    }

}