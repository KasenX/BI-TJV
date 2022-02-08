package cz.cvut.fit.tvj.eshop.business;

import cz.cvut.fit.tvj.eshop.dao.OrderJpaRepository;
import cz.cvut.fit.tvj.eshop.domain.Customer;
import cz.cvut.fit.tvj.eshop.domain.Order;
import cz.cvut.fit.tvj.eshop.domain.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService service;

    @MockBean
    CustomerService customerService;

    @MockBean
    OrderJpaRepository repository;

    @Test
    void readAllByCustomerId() {
        Mockito.when(customerService.readById(0L)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityStateException.class, () -> service.readAllByCustomerId(0L));
    }

    @Test
    void create() {
        Order order = new Order(1L, new Customer(), LocalDateTime.now(), new HashSet<>());
        Mockito.when(customerService.exists(order.getCustomer())).thenReturn(false);
        Assertions.assertThrows(EntityStateException.class, () -> service.create(order));
        Mockito.verify(customerService, Mockito.times(1)).exists(order.getCustomer());
    }

    @Test
    void update() {
        Order order = new Order(1L, new Customer(), LocalDateTime.now(), new HashSet<>());
        Mockito.when(customerService.exists(order.getCustomer())).thenReturn(false);
        Assertions.assertThrows(EntityStateException.class, () -> service.update(order));
        Mockito.verify(customerService, Mockito.times(1)).exists(order.getCustomer());
    }

    @Test
    void existsIdNull() {
        Order order = new Order(null, new Customer(), LocalDateTime.now(), new HashSet<>());
        Mockito.when(repository.existsById(order.getId())).thenReturn(false);

        Assertions.assertFalse(service.exists(order));
        Mockito.verify(repository, Mockito.never()).existsById(order.getId());
    }

    @Test
    void existsIdTaken() {
        Order order = new Order(1L, new Customer(), LocalDateTime.now(), new HashSet<>());
        Mockito.when(repository.existsById(order.getId())).thenReturn(true);

        Assertions.assertTrue(service.exists(order));
        Mockito.verify(repository, Mockito.times(1)).existsById(order.getId());
    }

    @Test
    void existsIdOk() {
        Order order = new Order(1L, new Customer(), LocalDateTime.now(), new HashSet<>());
        Mockito.when(repository.existsById(order.getId())).thenReturn(false);

        Assertions.assertFalse(service.exists(order));
        Mockito.verify(repository, Mockito.times(1)).existsById(order.getId());
    }
}