package de.telran.g240123mbelesson331082023.domain.entity.jpa;

import de.telran.g240123mbelesson331082023.domain.entity.Cart;
import de.telran.g240123mbelesson331082023.domain.entity.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "customer")
public class JpaCustomer implements Customer {
    private static final Logger logger = LoggerFactory.getLogger(JpaCustomer.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @Pattern(regexp = "[A-Z][a-z]{2,}")
    private String name;

    @Column(name = "age")
    @NotNull
    @Min(value = 18)
    @Max(value = 100)
    private int age;

    @Column(name = "email")
    @Email
    private String email;

    @OneToOne(
            mappedBy = "customer",
            cascade = CascadeType.ALL)
    private JpaCart cart;

    public JpaCustomer(int id, String name, JpaCart cart) {
        this.id = id;
        this.name = name;
        this.cart = cart;
        logger.info("Constructor called: JpaCustomer(int id, String name, JpaCart cart)");
    }

    public JpaCustomer() {
        logger.warn("Default constructor called: JpaCustomer()");
    }

    public JpaCustomer(int id, String name, int age, String email, JpaCart cart) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.cart = cart;
        logger.info("Full constructor called: JpaCustomer(int id, String name, int age, String email, JpaCart cart)");
    }

    public void setId(int id) {
        logger.debug("Method setId() called");
        this.id = id;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            logger.error("Name should not be null or empty!");
        }
        logger.info("Method setName() called");
        this.name = name;
    }

    public void setCart(JpaCart cart) {
        logger.info("Method setCart() called");
        this.cart = cart;
    }

    public void setAge(int age) {
        if (age < 18 || age > 100) {
            logger.error("Invalid age value!");
        }
        logger.info("Method setAge() called");
        this.age = age;
    }

    public void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            logger.error("Email should not be null or empty!");
        }
        logger.info("Method setEmail() called");
        this.email = email;
    }

    @Override
    public int getId() {
        logger.debug("Method getId() called");
        return id;
    }

    @Override
    public String getName() {
        logger.debug("Method getName() called");
        return name;
    }

    @Override
    public Cart getCart() {
        logger.debug("Method getCart() called");
        return cart;
    }

    public int getAge() {
        logger.debug("Method getAge() called");
        return age;
    }

    public String getEmail() {
        logger.debug("Method getEmail() called");
        return email;
    }
}
