package de.telran.g240123mbelesson331082023.domain.entity.jpa;

import de.telran.g240123mbelesson331082023.domain.entity.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class JpaProduct implements Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private double price;

    public JpaProduct(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public JpaProduct() {
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
