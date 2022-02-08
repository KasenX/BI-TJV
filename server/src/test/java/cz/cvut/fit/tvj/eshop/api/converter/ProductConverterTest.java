package cz.cvut.fit.tvj.eshop.api.converter;

import cz.cvut.fit.tvj.eshop.api.dto.CustomerDto;
import cz.cvut.fit.tvj.eshop.api.dto.ProductDto;
import cz.cvut.fit.tvj.eshop.domain.Customer;
import cz.cvut.fit.tvj.eshop.domain.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ProductConverterTest {

    Product product = new Product(1L, "product1", 1000.);
    ProductDto productDto = new ProductDto(1L, "product1", 1000.);

    @Test
    void toModel() {
        Assertions.assertEquals(product, ProductConverter.toModel(productDto));
    }

    @Test
    void fromModel() {
        Assertions.assertEquals(productDto, ProductConverter.fromModel(product));
    }

    @Test
    void toModels() {
        Assertions.assertArrayEquals(new Product[]{product}, ProductConverter.toModels(Arrays.stream((new ProductDto[]{productDto})).toList()).toArray());
    }

    @Test
    void fromModels() {
        Assertions.assertArrayEquals(new ProductDto[]{productDto}, ProductConverter.fromModels(Arrays.stream((new Product[]{product})).toList()).toArray());
    }
}