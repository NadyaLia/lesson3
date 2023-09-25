package de.telran.g240123mbelesson331082023.domain.entity.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.telran.g240123mbelesson331082023.domain.entity.Cart;
import de.telran.g240123mbelesson331082023.domain.entity.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "customer")
public class JpaCustomer implements Customer, UserDetails {
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

    @Column(name = "password")
    private String password;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_role",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

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

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
