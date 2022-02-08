package cz.cvut.fit.tvj.eshop.api.dto;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tvj.eshop.api.Views;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

public class OrderDto {

    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Public.class)
    private CustomerDto customer;
    @JsonView(Views.Internal.class)
    private LocalDateTime created;
    @JsonView(Views.Public.class)
    private Collection<ProductDto> products;

    public OrderDto() {

    }

    public OrderDto(Long id, CustomerDto customer, LocalDateTime created, Collection<ProductDto> products) {
        this.id = id;
        this.customer = customer;
        this.created = created;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Collection<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(Collection<ProductDto> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return Objects.equals(id, orderDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
