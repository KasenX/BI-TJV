package cz.cvut.fit.tvj.eshop.api.controller;

import cz.cvut.fit.tvj.eshop.api.converter.ProductConverter;
import cz.cvut.fit.tvj.eshop.api.dto.ProductDto;
import cz.cvut.fit.tvj.eshop.business.EntityStateException;
import cz.cvut.fit.tvj.eshop.business.ProductService;
import cz.cvut.fit.tvj.eshop.domain.Product;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    Collection<ProductDto> all() {
        return ProductConverter.fromModels(productService.readAll());
    }

    @GetMapping("/products/{id}")
    ProductDto one(@PathVariable long id) throws EntityStateException {
        return ProductConverter.fromModel(productService.readById(id).orElseThrow(() -> new EntityStateException(id)));
    }

    @PostMapping("/products")
    void create(@RequestBody ProductDto productDto) throws EntityStateException {
        Product product = ProductConverter.toModel(productDto);
        productService.create(product);
    }

    @PutMapping("/products/{id}")
    ProductDto update(@RequestBody ProductDto productDto, @PathVariable long id) throws EntityStateException {
        productService.readById(id).orElseThrow(() -> new EntityStateException(id));
        productDto.setId(id);
        productService.update(ProductConverter.toModel(productDto));
        return productDto;
    }

    @DeleteMapping("/products/{id}")
    void delete(@PathVariable long id) {
        productService.deleteById(id);
    }
}
