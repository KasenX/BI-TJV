package cz.cvut.fit.tvj.eshop.business;

import cz.cvut.fit.tvj.eshop.dao.OrderJpaRepository;
import cz.cvut.fit.tvj.eshop.domain.Customer;
import cz.cvut.fit.tvj.eshop.domain.Order;
import cz.cvut.fit.tvj.eshop.domain.Product;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OrderService extends AbstractCrudService<Long, Order, OrderJpaRepository> {

    private final CustomerService customerService;
    private final ProductService productService;

    protected OrderService(OrderJpaRepository repository, CustomerService customerService, ProductService productService) {
        super(repository);
        this.customerService = customerService;
        this.productService = productService;
    }

    public Collection<Order> readAllByCustomerId(Long id) throws EntityStateException {
        Customer customer = customerService.readById(id).orElseThrow(() -> new EntityStateException(id));
        return repository.findAllByCustomer(customer);
    }

    @Override
    public void create(Order entity) throws EntityStateException {
        if (!customerService.exists(entity.getCustomer()))
            throw new EntityStateException(entity.getCustomer());
        super.create(entity);
    }

    @Override
    public void update(Order entity) throws EntityStateException {
        if (!customerService.exists(entity.getCustomer()))
            throw new EntityStateException(entity.getCustomer());
        for (Product p : entity.getProducts()) {
            if (!productService.exists(p))
                throw new EntityStateException(p);
        }
        super.update(entity);
    }

    @Override
    public boolean exists(Order entity) {
        return !(entity.getId() == null || !repository.existsById(entity.getId()));
    }
}
