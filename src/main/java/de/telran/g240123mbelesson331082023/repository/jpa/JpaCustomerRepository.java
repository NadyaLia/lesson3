package de.telran.g240123mbelesson331082023.repository.jpa;

import de.telran.g240123mbelesson331082023.domain.entity.Customer;
import de.telran.g240123mbelesson331082023.domain.entity.jpa.JpaCustomer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaCustomerRepository extends JpaRepository<JpaCustomer, Integer> {
    @Transactional
    void deleteByName(String name);

    @Query(value = "SELECT avg(product.price) FROM product " +
            "JOIN cart_product ON product.id = cart_product.product_id " +
            "JOIN cart ON cart_product.cart_id = cart.id " +
            "JOIN customer ON cart.customer_id = customer.id " +
            "WHERE customer.id = ?1", nativeQuery = true)
    double getAveragePriceById(int id);

    @Transactional
    @Modifying
    @Query(value = "DELETE cart_product FROM cart_product " +
            "INNER JOIN cart ON cart.id = cart_product.cart_id " +
            "WHERE cart.customer_id = ?1 and cart_product.product_id = ?2", nativeQuery = true)
    void deleteFromCart(int customerId, int productId);

    @Transactional
    @Modifying
    @Query(value = "DELETE cart_product FROM cart_product " +
            "INNER JOIN cart ON cart.id = cart_product.cart_id " +
            "WHERE cart.customer_id = ?1", nativeQuery = true)
    void clearCart(int customerId);

    Customer findByName(String name);
}
