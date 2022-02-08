package cz.cvut.fit.tvj.eshop.business;

import cz.cvut.fit.tvj.eshop.dao.CustomerJpaRepository;
import cz.cvut.fit.tvj.eshop.dao.ProductJpaRepository;
import cz.cvut.fit.tvj.eshop.domain.Customer;
import cz.cvut.fit.tvj.eshop.domain.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {
    @Autowired
    private CustomerService service;

    @MockBean
    private CustomerJpaRepository repository;

    Customer customer = new Customer(1L, "customer");

    @Test
    void update() {
        Mockito.when(repository.existsByUsername(customer.getUsername())).thenReturn(true);
        Assertions.assertThrows(EntityStateException.class, () -> service.update(customer));
    }

    @Test
    void exists() {
        // ID EXISTS
        Mockito.when(repository.existsById(customer.getId())).thenReturn(true);
        Mockito.when(repository.existsByUsername(customer.getUsername())).thenReturn(false);

        Assertions.assertTrue(service.exists(customer));
        Mockito.verify(repository, Mockito.times(1)).existsById(customer.getId());
        Mockito.verify(repository, Mockito.times(0)).existsByUsername(customer.getUsername());

        // USERNAME EXISTS
        Mockito.when(repository.existsById(customer.getId())).thenReturn(false);
        Mockito.when(repository.existsByUsername(customer.getUsername())).thenReturn(true);

        Assertions.assertTrue(service.exists(customer));
        Mockito.verify(repository, Mockito.times(2)).existsById(customer.getId());
        Mockito.verify(repository, Mockito.times(1)).existsByUsername(customer.getUsername());

        // DOES NOT EXIST
        Mockito.when(repository.existsById(customer.getId())).thenReturn(false);
        Mockito.when(repository.existsByUsername(customer.getUsername())).thenReturn(false);

        Assertions.assertFalse(service.exists(customer));
        Mockito.verify(repository, Mockito.times(3)).existsById(customer.getId());
        Mockito.verify(repository, Mockito.times(2)).existsByUsername(customer.getUsername());
    }
}