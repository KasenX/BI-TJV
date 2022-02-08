package cz.cvut.fit.tvj.eshop.api.converter;

import cz.cvut.fit.tvj.eshop.api.dto.CustomerDto;
import cz.cvut.fit.tvj.eshop.domain.Customer;

import java.util.Collection;


public class CustomerConverter {

    public static Customer toModel(CustomerDto customerDto) {
        return new Customer(customerDto.getId(), customerDto.getUsername());
    }

    public static CustomerDto fromModel(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getUsername());
    }

    public static Collection<Customer> toModels(Collection<CustomerDto> customersDto) {
        return customersDto.stream().map(CustomerConverter::toModel).toList();
    }

    public static Collection<CustomerDto> fromModels(Collection<Customer> customers) {
        return customers.stream().map(CustomerConverter::fromModel).toList();
    }
}
