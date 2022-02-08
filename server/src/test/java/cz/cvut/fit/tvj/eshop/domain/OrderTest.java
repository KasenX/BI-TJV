package cz.cvut.fit.tvj.eshop.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Order order;
    private final Product product1 = new Product(1L, "product1", 100.);
    private final Product product2 = new Product(2L, "product2", 200.);

    @BeforeEach
    void setup() {
        Set<Product> products = new HashSet<>();
        products.add(product1);
        products.add(product2);
        order = new Order(1L, new Customer(), LocalDateTime.now(), products);
    }

    @Test
    void addProduct() {
        Product product = new Product(3L, "product3", 300.);
        order.addProduct(product);

        Assertions.assertArrayEquals(new Product[]{product1, product2, product}, order.getProducts().toArray());
    }

    @Test
    void removeProduct() {
        order.removeProduct(1L);

        Assertions.assertArrayEquals(new Product[]{product2}, order.getProducts().toArray());
    }
}