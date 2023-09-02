package de.telran.g240123mbelesson331082023.repository;

import de.telran.g240123mbelesson331082023.domain.database.Database;
import de.telran.g240123mbelesson331082023.domain.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {
    @Autowired
    private Database database;

    @Override
    public Customer findCustomerById(int id) {
        try {
            return (Customer) database.select("Select customer where id = " + id).get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        try {
            List<Object> selectAllCustomers = database.select("Select all customers");
            return selectAllCustomers.stream().map(o -> (Customer)o).toList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCustomerById(int id) {
        try {
            database.execute("Delete customer where id = " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCustomer(String name) {
        try {
            database.execute("Add new customer name - " + name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
