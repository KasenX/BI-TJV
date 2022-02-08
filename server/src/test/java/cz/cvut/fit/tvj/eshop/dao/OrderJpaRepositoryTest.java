package cz.cvut.fit.tvj.eshop.dao;

import cz.cvut.fit.tvj.eshop.domain.Customer;
import cz.cvut.fit.tvj.eshop.domain.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderJpaRepositoryTest {
    @Autowired
    private OrderJpaRepository repository;

    @Autowired
    private CustomerJpaRepository customerRepository;

    @Test
    void findAllByCustomerTest() {
        Customer customer1 = new Customer(1L, "customer1");
        Customer customer2 = new Customer(2L, "customer2");
        Order order1 = new Order(1L, customer1, LocalDateTime.now(), new HashSet<>());
        Order order2 = new Order(2L, customer1, LocalDateTime.now(), new HashSet<>());
        Order order3 = new Order(3L, customer2, LocalDateTime.now(), new HashSet<>());

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        repository.save(order1);
        repository.save(order2);
        repository.save(order3);

        var ret = repository.findAllByCustomer(customer1);
        Assertions.assertTrue(ret.contains(order1) && ret.contains(order2) && !ret.contains(order3));
    }
}