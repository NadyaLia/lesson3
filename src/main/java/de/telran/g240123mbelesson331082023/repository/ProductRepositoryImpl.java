package de.telran.g240123mbelesson331082023.repository;

import de.telran.g240123mbelesson331082023.domain.database.CommonDatabase;
import de.telran.g240123mbelesson331082023.domain.entity.CommonProduct;
import de.telran.g240123mbelesson331082023.domain.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    @Autowired
    private CommonDatabase dataBase;

    @Override
    public List<Product> getAll() {
        try {
            return dataBase.select("Select all products").stream().map(x -> (Product) x).toList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CommonProduct getById(int id) {
        try {
            return (CommonProduct) dataBase.select("Select product where id = " + id).get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void add(String name, double price) {
        try {
            dataBase.execute("Add new product name = " + name + " price = " + price);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try {
            dataBase.execute("Delete product where id = " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
