package cz.cvut.fit.tvj.eshop.business;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public abstract class AbstractCrudService<K, E, T extends JpaRepository<E, K>> {

    protected final T repository;

    protected AbstractCrudService(T repository) {
        this.repository = repository;
    }

    public void create(E entity) throws EntityStateException {
        if (exists(entity))
            throw new EntityStateException(entity);
        repository.save(entity);
    }

    public abstract boolean exists(E entity);

    public Optional<E> readById(K id) {
        return repository.findById(id);
    }

    public Collection<E> readAll() {
        return repository.findAll();
    }

    public void update(E entity) throws EntityStateException {
        if (!exists(entity))
            throw new EntityStateException(entity);
        repository.save(entity);
    }

    public void deleteById(K id) {
        repository.deleteById(id);
    }
}
