package de.telran.g240123mbelesson331082023.service.jpa;

import de.telran.g240123mbelesson331082023.domain.entity.Cart;
import de.telran.g240123mbelesson331082023.domain.entity.Customer;
import de.telran.g240123mbelesson331082023.domain.entity.jpa.JpaCart;
import de.telran.g240123mbelesson331082023.domain.entity.jpa.JpaCustomer;
import de.telran.g240123mbelesson331082023.domain.entity.jpa.JpaProduct;
import de.telran.g240123mbelesson331082023.domain.entity.jpa.Role;
import de.telran.g240123mbelesson331082023.repository.jpa.JpaCartRepository;
import de.telran.g240123mbelesson331082023.repository.jpa.JpaCustomerRepository;
import de.telran.g240123mbelesson331082023.repository.jpa.JpaProductRepository;
import de.telran.g240123mbelesson331082023.service.CustomerService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JpaCustomerService implements CustomerService, UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaCustomerService.class);

    @Autowired
    private JpaCustomerRepository repository;
    @Autowired
    private JpaCartRepository cartRepository;
    @Autowired
    private JpaProductRepository productRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public List<Customer> getAll() {
        return new ArrayList<>(repository.findAll());
    }

    @Override
    public Customer getById(int id) {
        LOGGER.info(String.format("INFO This is the customer with ID %d", id));
        LOGGER.warn(String.format("WARN This is the customer with ID %d", id));
        LOGGER.error(String.format("ERROR This is the customer with ID %d", id));

        return repository.findById(id).orElse(null);
    }

    @Override
    public void add(Customer customer) {

        JpaCustomer newCustomer = new JpaCustomer(0, customer.getName(), customer.getAge(), customer.getEmail(), null);
        JpaCustomer jpaCustomer = repository.save(newCustomer);

        int customerId = jpaCustomer.getId();

        JpaCart jpaCart = new JpaCart(jpaCustomer);

        cartRepository.save(jpaCart);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByName(String name) {
        repository.deleteByName(name);
    }

    @Override
    public int getCount() {
        return (int) repository.count();
    }

    @Override
    public double getTotalPriceById(int id) {
        return getById(id).getCart().getTotalCost();
    }

    @Override
    public double getAveragePriceById(int id) {
        return repository.getAveragePriceById(id);
    }

    @Transactional
    @Override
    public void addToCartById(int customerId, int productId) {
        JpaCustomer customer = repository.findById(customerId).orElseThrow(() -> new NoSuchElementException("Customer " +
                "with ID " + customerId + " not found"));
        JpaProduct product = productRepository.findById(productId).orElseThrow(() -> new NoSuchElementException(
                "Product with ID " + productId + " not found"));

        Cart cart = customer.getCart();
        cart.addProduct(product);

        JpaCart jpaCart = (JpaCart) cart;
        cartRepository.save(jpaCart);
    }

    @Transactional
    @Override
    public void deleteFromCart(int customerId, int productId) {
        repository.deleteFromCart(customerId, productId);
    }

    @Override
    public void clearCart(int customerId) {
        repository.clearCart(customerId);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Customer customer = repository.findByName(name);
        if (customer == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return (UserDetails) customer;
    }

    @Transactional
    public Customer updateOrSaveCustomer(JpaCustomer customer) {
        Optional<JpaCustomer> foundCustomer = repository.findById(customer.getId());
        if (foundCustomer != null) {
            JpaCustomer updatedCustomer = foundCustomer.get();
            updatedCustomer.setName(customer.getName());
            updatedCustomer.setAge(customer.getAge());
            updatedCustomer.setEmail(customer.getUsername());
            return repository.save(updatedCustomer);
        }

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1, "ROLE_USER"));
        customer.setRoles(roles);

        String encodedPassword = encoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
        return repository.save(customer);
    }
}
