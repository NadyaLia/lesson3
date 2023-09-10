package de.telran.g240123mbelesson331082023.controllers;

import de.telran.g240123mbelesson331082023.domain.entity.common.CommonCustomer;
import de.telran.g240123mbelesson331082023.domain.entity.Customer;
import de.telran.g240123mbelesson331082023.domain.entity.jpa.JpaCustomer;
import de.telran.g240123mbelesson331082023.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getAll() {
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable int id) {
        return customerService.getById(id);
    }

    @PostMapping
    public void add(@RequestBody CommonCustomer customer) {
        customerService.add(customer);
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
        return customerService.getTotalPriceById(id);
    }

    @GetMapping("/avgPrice/{id}")
    public double getAveragePriceById(@PathVariable int id) {
        return customerService.getAveragePriceById(id);
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
}
