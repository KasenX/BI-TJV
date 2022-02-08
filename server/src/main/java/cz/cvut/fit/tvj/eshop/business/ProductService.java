package cz.cvut.fit.tvj.eshop.business;

import cz.cvut.fit.tvj.eshop.dao.ProductJpaRepository;
import cz.cvut.fit.tvj.eshop.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends AbstractCrudService<Long, Product, ProductJpaRepository> {

    protected ProductService(ProductJpaRepository repository) {
        super(repository);
    }

    @Override
    public boolean exists(Product entity) {
        return !(entity.getId() == null || !repository.existsById(entity.getId()));
    }
}
