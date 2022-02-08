package cz.cvut.fit.tvj.eshop.api.controller;

import cz.cvut.fit.tvj.eshop.api.converter.CustomerConverter;
import cz.cvut.fit.tvj.eshop.api.dto.CustomerDto;
import cz.cvut.fit.tvj.eshop.business.CustomerService;
import cz.cvut.fit.tvj.eshop.business.EntityStateException;
import cz.cvut.fit.tvj.eshop.domain.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    Collection<CustomerDto> all() {
        return CustomerConverter.fromModels(customerService.readAll());
    }

    @GetMapping("/customers/{id}")
    CustomerDto one(@PathVariable long id) throws EntityStateException {
        return CustomerConverter.fromModel(customerService.readById(id).orElseThrow(() -> new EntityStateException(id)));
    }

    @PostMapping("/customers")
    void create(@RequestBody CustomerDto customerDto) throws EntityStateException {
        Customer customer = CustomerConverter.toModel(customerDto);
        customerService.create(customer);
    }

    @PutMapping("/customers/{id}")
    CustomerDto update(@RequestBody CustomerDto customerDto, @PathVariable long id) throws EntityStateException {
        customerService.readById(id).orElseThrow(() -> new EntityStateException(id));
        customerDto.setId(id);
        customerService.update(CustomerConverter.toModel(customerDto));
        return customerDto;
    }

    @DeleteMapping("/customers/{id}")
    void delete(@PathVariable long id) {
        customerService.deleteById(id);
    }

}
