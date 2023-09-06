package de.telran.g240123mbelesson331082023.repository;

import de.telran.g240123mbelesson331082023.domain.entity.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> getAll();

    Customer getById(int id);

    void add(String name);

    void delete(int id);

    void addToCartById(int customerId, int productId);

    void deleteFromCart(int customerId, int productId);

    void clearCart(int customerId);
}
