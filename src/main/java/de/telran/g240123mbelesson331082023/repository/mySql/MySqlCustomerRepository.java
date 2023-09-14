package de.telran.g240123mbelesson331082023.repository.mySql;

import de.telran.g240123mbelesson331082023.domain.entity.common.CommonCart;
import de.telran.g240123mbelesson331082023.domain.entity.common.CommonCustomer;
import de.telran.g240123mbelesson331082023.domain.entity.common.CommonProduct;
import de.telran.g240123mbelesson331082023.domain.entity.Customer;
import de.telran.g240123mbelesson331082023.repository.CustomerRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.telran.g240123mbelesson331082023.domain.database.MySqlConnector.getConnection;

public class MySqlCustomerRepository implements CustomerRepository {
    @Override
    public List<Customer> getAll() {
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM customer as c left join customer_product as cp on c.id = cp.customer_id " +
                    "left join product as p on cp.product_id = p.id;";
            // Здесь ваш код
            // Создать список клиентов и наполнить корзины каждого клиента их товарами
            // Учесть момент, что у клиента вообще может не быть никаких товаров,
            // в таком случае корзина просто должна быть пустая.
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            Map<Integer, Customer> customerMap = new HashMap<>();

            while (resultSet.next()) {
                int customerId = resultSet.getInt("c.id");
                String customerName = resultSet.getString("c.name");
                int productId = resultSet.getInt("p.id");
                String productName = resultSet.getString("p.name");
                double productPrice = resultSet.getDouble("p.price");
                int customerAge = resultSet.getInt("c.age");
                String customerEmail = resultSet.getString("c.email");

                Customer customer = customerMap.get(customerId);

                if(customer == null) {
                    customer = new CommonCustomer(customerId, customerName, customerAge, customerEmail,
                            new CommonCart());

                    customerMap.put(customerId, customer);
                }

                if (productId != 0) {
                    customer.getCart().addProduct(new CommonProduct(productId, productName, productPrice));
                }
            }
            return new ArrayList<>(customerMap.values());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer getById(int id) {
        try (Connection connection = getConnection()) {
            String query = String.format("SELECT * FROM customer as c left join customer_product as cp on c.id = " +
                    "cp.customer_id left join product as p on cp.product_id = p.id where c.id = %d;", id);
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            Customer customer = null;
            while (resultSet.next()) {
                int customerId = resultSet.getInt("c.id");
                String customerName = resultSet.getString("c.name");
                int productId = resultSet.getInt("p.id");
                String productName = resultSet.getString("p.name");
                double productPrice = resultSet.getDouble("p.price");
                if (customer == null) {
                    customer = new CommonCustomer(customerId, customerName,
                            new CommonCart());
                }
                if (productId != 0) {
                    customer.getCart().addProduct(new CommonProduct(productId, productName, productPrice));
                }
            }
            return customer;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(String name) {
        try (Connection connection = getConnection()) {
            String query = String.format("INSERT INTO `customer` (`name`) VALUES ('%s');", name);

            connection.createStatement().execute(query);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = getConnection()) {
            String query = String.format("DELETE FROM `customer` WHERE (`id` = '%d');", id);

            connection.createStatement().execute(query);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addToCartById(int customerId, int productId) {
        try (Connection connection = getConnection()) {
            String query = String.format("INSERT INTO `customer_product` (`customer_id`, `product_id`) VALUES " +
                    "('%d', '%d');", customerId, productId);

            connection.createStatement().executeUpdate(query);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFromCart(int customerId, int productId) {
        try (Connection connection = getConnection()) {
            String query = String.format("DELETE FROM `customer_product` WHERE (`customer_id` = '%d' and `product_id` " +
                    "= '%d');", customerId, productId);

            connection.createStatement().executeUpdate(query);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearCart(int customerId) {
        try (Connection connection = getConnection()) {
            String query = String.format("DELETE FROM `customer_product` WHERE (`customer_id` = '%d');", customerId);

            connection.createStatement().execute(query);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


