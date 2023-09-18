package de.telran.g240123mbelesson331082023.domain.entity.jpa;

import de.telran.g240123mbelesson331082023.domain.entity.Cart;
import de.telran.g240123mbelesson331082023.domain.entity.Product;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class JpaCart implements Cart {
    private static final Logger logger = LoggerFactory.getLogger(JpaCart.class);
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
        logger.info("Constructor called: JpaCart(int id, JpaCustomer customer, List<JpaProduct> products)");
    }

    public JpaCart() {
        logger.warn("Default constructor called: JpaCart()");
    }

    public JpaCart(List<JpaProduct> products) {
        this.products = products;
        logger.info("Constructor called: JpaCart(List<JpaProduct> products)");
    }

    public JpaCart(JpaCustomer customer) {
        this.customer = customer;
        logger.info("Constructor called: JpaCart(JpaCustomer customer)");
    }

    public void setId(int id) {
        logger.debug("Method setId() called");
        this.id = id;
    }

    public void setCustomer(JpaCustomer customer) {
        logger.debug("Method setCustomer() called");
        this.customer = customer;
    }

    public void setProducts(List<JpaProduct> products) {
        logger.debug("Method setProducts() called");
        this.products = products;
    }

    @Override
    public double getTotalCost() {
        logger.debug("Method getTotalCost() called");
        return products.stream().mapToDouble(x -> x.getPrice()).sum();
    }

    @Override
    public void addProduct(Product product) {
        logger.debug("Method addProduct() called");
        products.add(new JpaProduct(product.getId(), product.getName(), product.getPrice()));
    }

    @Override
    public List<Product> getProducts() {
        logger.debug("Method getProducts() called");
        return new ArrayList<>(products);
    }
}
