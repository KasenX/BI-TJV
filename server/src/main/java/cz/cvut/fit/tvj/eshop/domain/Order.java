package cz.cvut.fit.tvj.eshop.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "theorder")
public class Order {

    @Id
    @GeneratedValue(generator="order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_seq")
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;
    @Column(nullable = false)
    private LocalDateTime created;
    @ManyToMany
    private Set<Product> products;

    public Order() {

    }

    public Order(Long id, Customer customer, LocalDateTime created, Set<Product> products) {
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Collection<Product> getProducts() {
        return Collections.unmodifiableCollection(products);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(long id) {
        products.removeIf(p -> p.getId() == id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
