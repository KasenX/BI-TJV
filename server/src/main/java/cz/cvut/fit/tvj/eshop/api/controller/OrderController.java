package cz.cvut.fit.tvj.eshop.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tvj.eshop.api.Views;
import cz.cvut.fit.tvj.eshop.api.converter.OrderConverter;
import cz.cvut.fit.tvj.eshop.api.converter.ProductConverter;
import cz.cvut.fit.tvj.eshop.api.dto.OrderDto;
import cz.cvut.fit.tvj.eshop.api.dto.ProductDto;
import cz.cvut.fit.tvj.eshop.business.EntityStateException;
import cz.cvut.fit.tvj.eshop.business.OrderService;
import cz.cvut.fit.tvj.eshop.domain.Order;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @JsonView(Views.Public.class)
    @GetMapping("/orders")
    Collection<OrderDto> all() {
        return OrderConverter.fromModels(orderService.readAll());
    }

    @JsonView(Views.Public.class)
    @GetMapping("/orders/{id}")
    OrderDto one(@PathVariable long id) throws EntityStateException {
        return OrderConverter.fromModel(orderService.readById(id).orElseThrow(() -> new EntityStateException(id)));
    }

    @JsonView(Views.Public.class)
    @GetMapping("/customers/{id}/orders")
    Collection<OrderDto> allByCustomer(@PathVariable Long id) throws EntityStateException {
        return OrderConverter.fromModels(orderService.readAllByCustomerId(id));
    }

    @PostMapping("/orders")
    void create(@RequestBody OrderDto orderDto) throws EntityStateException {
        orderService.create(OrderConverter.toModel(orderDto));
    }

    @DeleteMapping("/orders/{id}")
    void delete(@PathVariable long id) {
        orderService.deleteById(id);
    }

    @PostMapping("/orders/{id}/products")
    void addProduct(@RequestBody ProductDto productDto, @PathVariable long id) throws EntityStateException {
        Order order = orderService.readById(id).orElseThrow(() -> new EntityStateException(id));
        order.addProduct(ProductConverter.toModel(productDto));
        orderService.update(order);
    }

    @DeleteMapping("/orders/{orderId}/products/{productId}")
    void removeProduct(@PathVariable long orderId, @PathVariable long productId) throws EntityStateException {
        Order order = orderService.readById(orderId).orElseThrow(() -> new EntityStateException(orderId));
        order.removeProduct(productId);
        orderService.update(order);
    }
}
