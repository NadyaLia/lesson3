package de.telran.g240123mbelesson331082023.domain.entity;

import java.util.List;

public interface Cart {
    double getTotalCost();
    void addProduct(Product product);
    List<Product> getProducts();
}
