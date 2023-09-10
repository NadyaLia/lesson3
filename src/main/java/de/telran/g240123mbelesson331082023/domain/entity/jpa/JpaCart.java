package de.telran.g240123mbelesson331082023.domain.entity.jpa;

import de.telran.g240123mbelesson331082023.domain.entity.Cart;
import de.telran.g240123mbelesson331082023.domain.entity.Product;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class JpaCart implements Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @OneToOne
    @JoinColumn(name = "customer_id")
    private JpaCustomer customer;
    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<JpaProduct> products;

    public JpaCart(int id, JpaCustomer customer, List<JpaProduct> products) {
        this.id = id;
        this.customer = customer;
        this.products = products;
    }

    public JpaCart() {
    }

    public JpaCart(List<JpaProduct> products) {
        this.products = products;
    }

    public JpaCart(JpaCustomer customer) {
        this.customer = customer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomer(JpaCustomer customer) {
        this.customer = customer;
    }

    public void setProducts(List<JpaProduct> products) {
        this.products = products;
    }

    @Override
    public double getTotalCost() {
        return products.stream().mapToDouble(x -> x.getPrice()).sum();
    }

    @Override
    public void addProduct(Product product) {
        products.add(new JpaProduct(product.getId(), product.getName(), product.getPrice()));
    }

    @Override
    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }
}
