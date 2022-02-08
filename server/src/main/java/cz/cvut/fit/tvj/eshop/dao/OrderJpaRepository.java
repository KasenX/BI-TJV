package cz.cvut.fit.tvj.eshop.dao;

import cz.cvut.fit.tvj.eshop.domain.Customer;
import cz.cvut.fit.tvj.eshop.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCustomer(Customer customer);
}
