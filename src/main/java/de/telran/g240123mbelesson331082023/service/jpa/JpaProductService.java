package de.telran.g240123mbelesson331082023.service.jpa;

import de.telran.g240123mbelesson331082023.domain.entity.Product;
import de.telran.g240123mbelesson331082023.domain.entity.common.CommonProduct;
import de.telran.g240123mbelesson331082023.domain.entity.jpa.JpaProduct;
import de.telran.g240123mbelesson331082023.repository.jpa.JpaProductRepository;
import de.telran.g240123mbelesson331082023.service.ProductService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JpaProductService implements ProductService {
    private static final Logger LOGGER = LogManager.getLogger(JpaProductService.class);
    @Autowired
    private JpaProductRepository repository;

    @Override
    public List<Product> getAll() {
        return new ArrayList<>(repository.findAll());
    }

    @Override
    public Product getById(int id) {
        /*LOGGER.log(Level.INFO, String.format("INFO This is the product with ID %d", id));
        LOGGER.log(Level.WARN, String.format("WARN This is the product with ID %d", id));
        LOGGER.log(Level.ERROR, String.format("ERROR This is the product with ID %d", id));*/

       /* LOGGER.info(String.format("INFO This is the product with ID %d", id));
        LOGGER.warn(String.format("WARN This is the product with ID %d", id));
        LOGGER.error(String.format("ERROR This is the product with ID %d", id));*/

        return repository.findById(id).get();
    }

    @Override
    public void add(CommonProduct product) {
        repository.save(new JpaProduct(0, product.getName(), product.getPrice()));
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByName(String name) {
        repository.deleteByName(name);
    }

    @Override
    public int getCount() {
        return (int) repository.count();
    }

    @Override
    public double getTotalPrice() {
        return repository.getTotalPrice();
    }

    @Override
    public double getAveragePrice() {
        return repository.getAveragePrice();
    }

    public void test(JpaProduct product) {
        product.setName("New name");
    }
}
