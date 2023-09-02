package de.telran.g240123mbelesson331082023.service;

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
        return repository.getAllCustomers();
    }

    @Override
    public Customer getById(int id) {
        return repository.findCustomerById(id);
    }

    @Override
    public void add(Customer customer) {
        repository.addCustomer(customer.getName());
    }

    @Override
    public void deleteById(int id) {
        repository.deleteCustomerById(id);
    }
    @Override
    public void deleteByName(String name) {
        int idToDelete = repository.getAllCustomers().stream().filter(x -> x.getName().equals(name)).findFirst().get().
                getId();
        repository.deleteCustomerById(idToDelete);
    }

    @Override
    public int getCount() {
        return repository.getAllCustomers().size();
    }

    @Override
    public double getTotalPriceById(int id) {
        return repository.findCustomerById(id).getCart().getTotalCost();
    }

    @Override
    public double getAveragePriceById(int id) {
        CommonCart cart = (CommonCart) repository.findCustomerById(id).getCart();
        if (cart.getProducts().size() == 0) {
            return 0;
        }
        return cart.getTotalCost() / cart.getProducts().size();
    }

    @Override
    public void addToCartById(int customerId, int productId) {
        Customer customer = repository.findCustomerById(customerId);
        Product product = productService.getById(productId);
        customer.getCart().addProduct(product);
    }
    @Override
    public void deleteFromCart(int customerId, int productId) {
        Customer customer = repository.findCustomerById(customerId);
        customer.getCart().getProducts().removeIf(x -> x.getId() == productId);
    }

    @Override
    public void clearCart(int customerId) {
        Customer customer = repository.findCustomerById(customerId);
        customer.getCart().getProducts().clear();
    }
}
