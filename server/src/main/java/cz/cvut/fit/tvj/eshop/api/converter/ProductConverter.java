package cz.cvut.fit.tvj.eshop.api.converter;

import cz.cvut.fit.tvj.eshop.api.dto.ProductDto;
import cz.cvut.fit.tvj.eshop.domain.Product;

import java.util.Collection;


public class ProductConverter {

    public static Product toModel(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getName(), productDto.getPrice());
    }

    public static ProductDto fromModel(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice());
    }

    public static Collection<Product> toModels(Collection<ProductDto> productsDto) {
        return productsDto.stream().map(ProductConverter::toModel).toList();
    }

    public static Collection<ProductDto> fromModels(Collection<Product> products) {
        return products.stream().map(ProductConverter::fromModel).toList();
    }
}
