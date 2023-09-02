package de.telran.g240123mbelesson331082023.domain.database;

import de.telran.g240123mbelesson331082023.domain.entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommonDatabase implements Database {
    private List<Product> products = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private int idProductCounter = 5;
    private int idCustomerCounter = 3;

    public CommonDatabase() {
        products.add(new CommonProduct(1, "Milk", 3.5));
        products.add(new CommonProduct(2, "Sugar", 2.0));
        products.add(new CommonProduct(3, "Butter", 6.0));
        products.add(new CommonProduct(4, "Oat flakes", 1.5));
        products.add(new CommonProduct(5, "Eggs", 4.5));

        customers.add(new CommonCustomer(1, "John", new CommonCart()));
        customers.add(new CommonCustomer(2, "Alice", new CommonCart()));
        customers.add(new CommonCustomer(3, "Kat", new CommonCart()));
    }

    @Override
    public void execute(String query) throws SQLException {
        if(!query.startsWith("Add") && !query.startsWith("Delete")) {
            throw new SQLException();
        }
        String[] parts = query.split(" ");
        if (parts[0].equals("Add")) {

            if (parts[2].equals("customer")) {
                customers.add(new CommonCustomer(++idCustomerCounter, parts[5], new CommonCart()));
            }
            if (parts[2].equals("product")) {
                products.add(new CommonProduct(++idProductCounter, parts[5], Double.parseDouble(parts[8])));
            }
        }
        if (parts[0].equals("Delete")) {
            if (parts[1].equals("customer")) {
                customers.removeIf(x -> x.getId() == Integer.parseInt(parts[5]));
            }
            if (parts[1].equals("product")) {
                products.removeIf(x -> x.getId() == Integer.parseInt(parts[5]));
            }
        }
    }

//  "Select all customers" - такие запросы выполняет метод select
//	"Select customer where id = 1" - такие запросы выполняет метод select
    @Override
    public List<Object> select(String query) throws SQLException {
        if (!query.startsWith("Select")) {
            throw new SQLException();
        }

        String[] parts = query.split(" ");
        List<Object> result = new ArrayList<>();

        if (parts.length == 3) {
            if (parts[2].equals("customers")) {
                result.addAll(customers);
            }

            if (parts[2].equals("products")) {
                result.addAll(products);
            }
        }

        if (parts.length == 6) {
            if (parts[1].equals("customer")) {
                result.add(customers.stream().filter(x -> x.getId() == Integer.parseInt(parts[5])).toList().get(0));
            }

            if (parts[1].equals("product")) {
                result.add(products.stream().filter(x -> x.getId() == Integer.parseInt(parts[5])).toList().get(0));
            }
        }

        return result;
    }
}
