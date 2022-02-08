package cz.cvut.fit.tvj.eshop.business;

import cz.cvut.fit.tvj.eshop.dao.ProductJpaRepository;
import cz.cvut.fit.tvj.eshop.domain.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService service;

    @MockBean
    ProductJpaRepository repository;

    @Test
    void existsIdNull() {
        Product product = new Product(null, "product", 1000.);
        Mockito.when(repository.existsById(product.getId())).thenReturn(false);

        Assertions.assertFalse(service.exists(product));
        Mockito.verify(repository, Mockito.never()).existsById(product.getId());
    }

    @Test
    void existsIdTaken() {
        Product product = new Product(1L, "product", 1000.);
        Mockito.when(repository.existsById(product.getId())).thenReturn(true);

        Assertions.assertTrue(service.exists(product));
        Mockito.verify(repository, Mockito.times(1)).existsById(product.getId());
    }

    @Test
    void existsIdOk() {
        Product product = new Product(1L, "product", 1000.);
        Mockito.when(repository.existsById(product.getId())).thenReturn(false);

        Assertions.assertFalse(service.exists(product));
        Mockito.verify(repository, Mockito.times(1)).existsById(product.getId());
    }
}