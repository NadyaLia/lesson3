package de.telran.g240123mbelesson331082023.service;

import de.telran.g240123mbelesson331082023.domain.entity.common.CommonProduct;
import de.telran.g240123mbelesson331082023.domain.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();

    Product getById(int id);

    void add(CommonProduct product);

    void deleteById(int id);

    void deleteByName(String name);

    int getCount();

    double getTotalPrice();

    double getAveragePrice();
}
