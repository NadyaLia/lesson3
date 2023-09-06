package de.telran.g240123mbelesson331082023.service;

import de.telran.g240123mbelesson331082023.domain.entity.Cart;
import de.telran.g240123mbelesson331082023.domain.entity.CommonCart;
import de.telran.g240123mbelesson331082023.domain.entity.Customer;
import de.telran.g240123mbelesson331082023.domain.entity.Product;
import de.telran.g240123mbelesson331082023.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CommonCustomerService implements CustomerService {
    @Autowired
    private CustomerRepository repository;

    @Autowired
    private ProductService productService;

    @Override
    public List<Customer> getAll() {
        return repository.getAll();
    }

    @Override
    public Customer getById(int id) {
        return repository.getById(id);
    }

    @Override
    public void add(Customer customer) {
        repository.add(customer.getName());
    }

    @Override
    public void deleteById(int id) {
        repository.delete(id);
    }

    @Override
    public void deleteByName(String name) {
        int idToDelete = repository.getAll().stream().filter(x -> x.getName().equals(name)).findFirst().get().getId();
        repository.delete(idToDelete);
    }

    @Override
    public int getCount() {
        return repository.getAll().size();
    }

    @Override
    public double getTotalPriceById(int id) {
        return repository.getById(id).getCart().getTotalCost();
    }

    @Override
    public double getAveragePriceById(int id) {
        Cart cart = repository.getById(id).getCart();
        return cart.getTotalCost() / cart.getProducts().size();
    }

    @Override
    public void addToCartById(int customerId, int productId) {
        repository.addToCartById(customerId, productId);
    }

    @Override
    public void deleteFromCart(int customerId, int productId) {
        repository.deleteFromCart(customerId, productId);
    }

    @Override
    public void clearCart(int customerId) {
        repository.clearCart(customerId);
    }
}
