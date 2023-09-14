package de.telran.g240123mbelesson331082023.domain.entity.jpa;

import de.telran.g240123mbelesson331082023.domain.entity.Cart;
import de.telran.g240123mbelesson331082023.domain.entity.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "customer")
public class JpaCustomer implements Customer {
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
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;

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

    public JpaCustomer(int id, String name, int age, String email, JpaCart cart) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.cart = cart;
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

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }
}
