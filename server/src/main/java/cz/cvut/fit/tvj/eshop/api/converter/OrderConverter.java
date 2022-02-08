package cz.cvut.fit.tvj.eshop.api.converter;

import cz.cvut.fit.tvj.eshop.api.dto.OrderDto;
import cz.cvut.fit.tvj.eshop.domain.Order;

import java.util.Collection;
import java.util.HashSet;

public class OrderConverter {

    public static Order toModel(OrderDto orderDto) {
        return new Order(orderDto.getId(), CustomerConverter.toModel(
                orderDto.getCustomer()), orderDto.getCreated(), new HashSet<>(ProductConverter.toModels(orderDto.getProducts())));
    }

    public static OrderDto fromModel(Order order) {
        return new OrderDto(order.getId(),CustomerConverter.fromModel(
                order.getCustomer()), order.getCreated(), ProductConverter.fromModels(order.getProducts()));
    }

    public static Collection<Order> toModels(Collection<OrderDto> ordersDto) {
        return ordersDto.stream().map(OrderConverter::toModel).toList();
    }

    public static Collection<OrderDto> fromModels(Collection<Order> orders) {
        return orders.stream().map(OrderConverter::fromModel).toList();
    }
}
