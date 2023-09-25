package de.telran.g240123mbelesson331082023.controllers;

import de.telran.g240123mbelesson331082023.domain.entity.Product;
import de.telran.g240123mbelesson331082023.domain.entity.common.CommonCustomer;
import de.telran.g240123mbelesson331082023.domain.entity.Customer;
import de.telran.g240123mbelesson331082023.domain.entity.jpa.JpaCustomer;
import de.telran.g240123mbelesson331082023.domain.entity.jpa.JpaProduct;
import de.telran.g240123mbelesson331082023.exception_layer.exceptions.CartIsEmptyException;
import de.telran.g240123mbelesson331082023.exception_layer.exceptions.CustomerNotFoundException;
import de.telran.g240123mbelesson331082023.exception_layer.exceptions.EntityValidationException;
import de.telran.g240123mbelesson331082023.service.CustomerService;
import de.telran.g240123mbelesson331082023.service.jpa.JpaCustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController implements Controller {
    @Autowired
    private JpaCustomerService customerService;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping
    public List<Customer> getAll() {
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable int id) {
        Customer customer = customerService.getById(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        return customer;
    }

    @PostMapping
    public Customer add(@Valid @RequestBody CommonCustomer customer) {
        try {
            customerService.add(customer);
            return customer;
        } catch (Exception e) {
            throw new EntityValidationException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        customerService.deleteById(id);
    }

    @DeleteMapping("/name/{name}")
    public void deleteByName(@PathVariable String name) {
        customerService.deleteByName(name);
    }

    @GetMapping("/count")
    public int getCount() {
        return customerService.getCount();
    }

    @GetMapping("/totalPrice/{id}")
    public double getTotalPriceById(@PathVariable int id) {
        double totalPrice = customerService.getTotalPriceById(id);
        if (totalPrice == 0) {
            throw new CartIsEmptyException("The cart is empty. Cannot get total price.");
        }
        return totalPrice;
    }

    @GetMapping("/avgPrice/{id}")
    public double getAveragePriceById(@PathVariable int id) {
        double averagePrice = customerService.getAveragePriceById(id);
        return averagePrice;
    }

    @PostMapping("/cart/add/{customerId}/{productId}")
    public void addToCartById(@PathVariable int customerId, @PathVariable int productId) {
        customerService.addToCartById(customerId, productId);
    }

    @DeleteMapping("/cart/delete/{customerId}/{productId}")
    public void deleteFromCart(@PathVariable int customerId, @PathVariable int productId) {
        customerService.deleteFromCart(customerId, productId);
    }

    @DeleteMapping("/cart/clear/{customerId}")
    public void clearCart(@PathVariable int customerId) {
        customerService.clearCart(customerId);
    }

    @PutMapping("/update/{id}")
    public Customer update(@PathVariable int id, @RequestBody JpaCustomer customer) {
        if (!(customer.getId() == id)) {
            throw new IllegalArgumentException("Customer ID and request body must much");
        }
        return customerService.updateOrSaveCustomer(customer);
    }
}
