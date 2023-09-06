package de.telran.g240123mbelesson331082023.service;

import de.telran.g240123mbelesson331082023.domain.entity.CommonProduct;
import de.telran.g240123mbelesson331082023.domain.entity.Product;
import de.telran.g240123mbelesson331082023.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
public class CommonProductService implements ProductService {
    @Autowired
    private ProductRepository repository;

    @Override
    public List<Product> getAll() {
        return repository.getAll();
    }

    @Override
    public Product getById(int id) {
        return repository.getById(id);
    }

    @Override
    public void add(CommonProduct product) {
        repository.add(product.getName(), product.getPrice());
    }

    @Override
    public void deleteById(int id) {
        repository.delete(id);
    }

    @Override
    public void deleteByName(String name) {
        Product product = repository.getAll().stream().filter(x -> x.getName().equals(name)).findFirst().get();
        repository.delete(product.getId());
    }

    @Override
    public int getCount() {
        return repository.getAll().size();
    }

    @Override
    public double getTotalPrice() {
        return repository.getAll().stream().mapToDouble(x -> x.getPrice()).sum();
    }

    @Override
    public double getAveragePrice() {
        return getTotalPrice() / getCount();
    }
}
