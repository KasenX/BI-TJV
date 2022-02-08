package cz.cvut.fit.tvj.eshop.business;

import cz.cvut.fit.tvj.eshop.dao.CustomerJpaRepository;
import cz.cvut.fit.tvj.eshop.domain.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends AbstractCrudService<Long, Customer, CustomerJpaRepository> {

    protected CustomerService(CustomerJpaRepository repository) {
        super(repository);
    }

    @Override
    public void update(Customer entity) throws EntityStateException {
        // The username alrady exists
        if (repository.existsByUsername(entity.getUsername()))
            throw new EntityStateException(entity);
        super.update(entity);
    }

    @Override
    public boolean exists(Customer entity) {
        // The customer already exists
        if (entity.getId() != null && repository.existsById(entity.getId()))
            return true;
        // The username alrady exists
        return entity.getUsername() != null && repository.existsByUsername(entity.getUsername());
    }
}
