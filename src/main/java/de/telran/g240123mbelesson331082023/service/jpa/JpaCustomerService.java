package de.telran.g240123mbelesson331082023.service.jpa;

import de.telran.g240123mbelesson331082023.domain.entity.Cart;
import de.telran.g240123mbelesson331082023.domain.entity.Customer;
import de.telran.g240123mbelesson331082023.domain.entity.jpa.JpaCart;
import de.telran.g240123mbelesson331082023.domain.entity.jpa.JpaCustomer;
import de.telran.g240123mbelesson331082023.domain.entity.jpa.JpaProduct;
import de.telran.g240123mbelesson331082023.repository.jpa.JpaCartRepository;
import de.telran.g240123mbelesson331082023.repository.jpa.JpaCustomerRepository;
import de.telran.g240123mbelesson331082023.repository.jpa.JpaProductRepository;
import de.telran.g240123mbelesson331082023.service.CustomerService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class JpaCustomerService implements CustomerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaCustomerService.class);

    @Autowired
    private JpaCustomerRepository repository;
    @Autowired
    private JpaCartRepository cartRepository;
    @Autowired
    private JpaProductRepository productRepository;

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
}
