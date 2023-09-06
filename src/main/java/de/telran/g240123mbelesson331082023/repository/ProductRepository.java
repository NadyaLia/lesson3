package de.telran.g240123mbelesson331082023.repository;

import de.telran.g240123mbelesson331082023.domain.entity.CommonProduct;
import de.telran.g240123mbelesson331082023.domain.entity.Product;

import java.util.List;

public interface ProductRepository {

        List<Product> getAll();

        Product getById(int id);

        void add(String name, double price);

        void delete(int id);
}
