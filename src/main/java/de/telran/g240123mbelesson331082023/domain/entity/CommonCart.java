package de.telran.g240123mbelesson331082023.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class CommonCart implements Cart {
    private double totalCost;
    private List<Product> products = new ArrayList<>();


    @Override
    public double getTotalCost() {
        return products.stream().mapToDouble(x -> x.getPrice()).reduce((x1, x2) -> x1 + x2).orElse(0);
    }

    @Override
    public void addProduct(Product product) {
        products.add(product);
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }
}
