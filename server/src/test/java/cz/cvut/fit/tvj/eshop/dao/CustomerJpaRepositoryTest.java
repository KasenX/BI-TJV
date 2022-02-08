package cz.cvut.fit.tvj.eshop.dao;

import cz.cvut.fit.tvj.eshop.domain.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerJpaRepositoryTest {

    @Autowired
    CustomerJpaRepository repository;

    @Test
    void existsByUsername() {
        Customer customer = new Customer(null, "customer3");
        repository.save(customer);
        Assertions.assertTrue(repository.existsByUsername("customer3"));
        Assertions.assertFalse(repository.existsByUsername("customer4"));
    }
}