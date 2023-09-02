package de.telran.g240123mbelesson331082023.config;

import de.telran.g240123mbelesson331082023.domain.database.CommonDatabase;
import de.telran.g240123mbelesson331082023.domain.database.Database;
import de.telran.g240123mbelesson331082023.repository.CustomerRepository;
import de.telran.g240123mbelesson331082023.repository.CustomerRepositoryImpl;
import de.telran.g240123mbelesson331082023.repository.ProductRepository;
import de.telran.g240123mbelesson331082023.repository.ProductRepositoryImpl;
import de.telran.g240123mbelesson331082023.service.CommonCustomerService;
import de.telran.g240123mbelesson331082023.service.CommonProductService;
import de.telran.g240123mbelesson331082023.service.CustomerService;
import de.telran.g240123mbelesson331082023.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Database database() {
        return new CommonDatabase();
    }

    @Bean
    public CustomerService customerService() {
        return new CommonCustomerService();
    }

    @Bean
    public ProductService productService() {
        return new CommonProductService();
    }

    @Bean
    public ProductRepository productRepository() {
        return new ProductRepositoryImpl();
    }

    @Bean
    public CustomerRepository customerRepository() {
        return new CustomerRepositoryImpl();
    }
}
