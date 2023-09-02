package de.telran.g240123mbelesson331082023.service;

import de.telran.g240123mbelesson331082023.domain.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAll();

    Customer getById(int id);

    void add(Customer customer);

    void deleteById(int id);

    void deleteByName(String name);

    int getCount();

    double getTotalPriceById(int id);

    double getAveragePriceById(int id);

    void addToCartById(int customerId, int productId);

    void deleteFromCart(int customerId, int productId);

    void clearCart(int customerId);
}
