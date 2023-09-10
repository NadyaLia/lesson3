package de.telran.g240123mbelesson331082023.domain.entity.jpa;

import de.telran.g240123mbelesson331082023.domain.entity.Cart;
import de.telran.g240123mbelesson331082023.domain.entity.Customer;
import jakarta.persistence.*;

@Entity
@Table(name = "customer")
public class JpaCustomer implements Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @OneToOne(
            mappedBy = "customer",
            cascade = CascadeType.ALL)
    private JpaCart cart;

    public JpaCustomer(int id, String name, JpaCart cart) {
        this.id = id;
        this.name = name;
        this.cart = cart;
    }

    public JpaCustomer() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCart(JpaCart cart) {
        this.cart = cart;
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
    public Cart getCart() {
        return cart;
    }
}
