package de.telran.g240123mbelesson331082023.repository;

import de.telran.g240123mbelesson331082023.domain.entity.Customer;

import java.util.List;

public interface CustomerRepository {
    Customer findCustomerById(int id);
    List<Customer> getAllCustomers();
    void deleteCustomerById(int id);
    void addCustomer(String name);
}
