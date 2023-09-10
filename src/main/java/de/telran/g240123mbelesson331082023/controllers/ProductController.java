package de.telran.g240123mbelesson331082023.controllers;

import de.telran.g240123mbelesson331082023.domain.entity.common.CommonProduct;
import de.telran.g240123mbelesson331082023.domain.entity.Product;
import de.telran.g240123mbelesson331082023.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable int id) {
        return productService.getById(id);
    }

    @PostMapping
    public void add(@RequestBody CommonProduct product) {
        productService.add(product);
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
}
