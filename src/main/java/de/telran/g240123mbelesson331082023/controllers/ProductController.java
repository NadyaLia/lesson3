package de.telran.g240123mbelesson331082023.controllers;

import de.telran.g240123mbelesson331082023.domain.entity.common.CommonProduct;
import de.telran.g240123mbelesson331082023.domain.entity.Product;
import de.telran.g240123mbelesson331082023.domain.entity.jpa.JpaProduct;
import de.telran.g240123mbelesson331082023.exception_layer.Response;
import de.telran.g240123mbelesson331082023.exception_layer.exceptions.*;
import de.telran.g240123mbelesson331082023.service.ProductService;
import de.telran.g240123mbelesson331082023.service.jpa.JpaProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController implements Controller {
    @Autowired
    private JpaProductService productService;

    @GetMapping
    public List<Product> getAll() {
        ((JpaProductService) productService).test(new JpaProduct(0, "Test Name", 100));
        List<Product> products = productService.getAll();
        if (products.size() == 8) {
            throw new ThirdTestException("Products list is empty!");
        }
        return products;
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable int id) {
        Product product = productService.getById(id);

        if (product == null) {
            throw new ProductNotFoundException("Product not found");
        }
        return product;
    }

    @PostMapping
    public Product add(@Valid @RequestBody CommonProduct product) {
        try {
            productService.add(product);
            return product;
        } catch (Exception e) {
            throw new EntityValidationException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        productService.deleteById(id);
    }

    @DeleteMapping("/name/{name}")
    public void deleteByName(@PathVariable String name) {
        productService.deleteByName(name);
    }

    @GetMapping("/count")
    public int getCount() {
        return productService.getCount();
    }

    @GetMapping("/totalPrice")
    public double getTotalPrice() {
        return productService.getTotalPrice();
    }

    @GetMapping("/avgPrice")
    public double getAveragePrice() {
        return productService.getAveragePrice();
    }

    @PutMapping("/update/{id}")
    public Product update(@PathVariable int id, @RequestBody JpaProduct product) {
        if (!(product.getId() == id)) {
            throw new IllegalArgumentException("Product ID and request body must much");
        }
        return productService.updateOrSaveProduct(product);
    }
}
