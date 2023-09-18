package de.telran.g240123mbelesson331082023.domain.entity.jpa;

import de.telran.g240123mbelesson331082023.domain.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "product")
public class JpaProduct implements Product {
    private static final Logger logger = LoggerFactory.getLogger(JpaProduct.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    @NotNull
    @NotBlank
     @Pattern(regexp = "[A-Z][a-z]{2,}")
    private String name;
    @Column(name = "price")
    @NotNull
    // @Min(value = 1 / 100)
    @Min(value = 1)
    @Max(value = 999999)
    private double price;

    public JpaProduct(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        logger.info("Constructor called: JpaProduct(int id, String name, double price)");
    }

    public JpaProduct() {
        logger.warn("Default constructor called: JpaProduct()");
    }

    @Override
    public String toString() {
        logger.debug("Method toString() called");
        return "JpaProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
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
    public double getPrice() {
        logger.debug("Method getPrice() called");
        return price;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            logger.error("Name should not be null or empty!");
        }
        logger.info("Method setName() called");
        this.name = name;
    }
}
