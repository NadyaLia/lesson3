package de.telran.g240123mbelesson331082023.logging_layer;

import de.telran.g240123mbelesson331082023.domain.entity.Customer;
import de.telran.g240123mbelesson331082023.domain.entity.Product;
import de.telran.g240123mbelesson331082023.domain.entity.jpa.Task;
import de.telran.g240123mbelesson331082023.repository.jpa.JpaTaskRepository;
import de.telran.g240123mbelesson331082023.service.CustomerService;
import de.telran.g240123mbelesson331082023.service.ProductService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class ScheduledAspectLogging {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledAspectLogging.class);
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final List<Product> productsInCart = new ArrayList<>();
    private final Map<Integer, Double> specialOffers = new HashMap<Integer, Double>();

    @Autowired
    private JpaTaskRepository repository;

    // 3. После запроса конкретного продукта через 15 секунд отправить персональное предложение
    //        на этот продукт с ценой на 5-10% (рандомно) ниже, чем базовая цена.
    //        Имитировать отправку в виде вывода в консоль и логирования.
    //        Данная задача должна выполняться при помощи АОП и сохраняться в БД.
    @Pointcut("execution(* de.telran.g240123mbelesson331082023.service.jpa.JpaProductService.getById(..))")
    public void getProductById() {}

    @AfterReturning(value = "getProductById()", returning = "product")
    public void sendPersonalizedOffer(Product product) {
        Task scheduledTask = new Task();
        scheduledTask.setDescription("PersonalizedOffer");
        scheduledTask.setExecutedAt(Timestamp.valueOf(LocalDateTime.now()));
        repository.save(scheduledTask);

        double offerPrice = product.getPrice() - (ThreadLocalRandom.current().nextInt(5, 11) *
                product.getPrice() / 100);
        scheduler.schedule(() -> logger.info("Special offer for product " + product.getName() + " :" + offerPrice),
                15, TimeUnit.SECONDS);
    }

    // 4. После того как покупатель очистил корзину, через 20 секунд выбрать из корзины случайный товар,
    //        который там был, сделать на него скидку 15% и предложить покупателю всё-таки
    //        приобрести все эти товары, вывести все товары (один с новой ценой), а также старую и новую стоимость корзины.
    //        Данная задача должна выполняться при помощи АОП и сохраняться в БД.

    @Pointcut("execution(* de.telran.g240123mbelesson331082023.service.jpa.JpaCustomerService.clearCart(..))")
    public void clearCart() {}

    @Before(value = "clearCart()", argNames = "customerId")
    public void captureCartState(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        int customerId = (int) args[0];
        CustomerService customerService = (CustomerService) joinPoint.getTarget();
        List<Product> currentProducts =customerService.getById(customerId).getCart().getProducts();
        productsInCart.clear();
        productsInCart.addAll(currentProducts);
    }

    @After("clearCart()")
    public void sendOfferAfterCartCleared() {
        if (productsInCart.isEmpty()) {
            return;
        }

        Task scheduledTask = new Task();
        scheduledTask.setDescription("SpecialOffer");
        scheduledTask.setExecutedAt(Timestamp.valueOf(LocalDateTime.now()));
        repository.save(scheduledTask);

        scheduler.schedule(() -> {
            int randomIndex = new Random().nextInt(productsInCart.size());
            Product randomProduct = productsInCart.get(randomIndex);
            double newPrice = randomProduct.getPrice() * 0.85;

            specialOffers.put(randomProduct.getId(), newPrice);

            double oldTotalPrice = productsInCart.stream().mapToDouble(Product::getPrice).sum();
            double newTotalPrice = oldTotalPrice - randomProduct.getPrice() + newPrice;

            logger.info("Special offer!");
            for (Product product : productsInCart) {
                if (product.equals(randomProduct)) {
                    logger.info(product.getName() + " at special price: " + newPrice);
                } else {
                    logger.info(product.getName() + " at regular price: " + product.getPrice());
                }
            }

            logger.info("Old total price: " + oldTotalPrice);
            logger.info("New total price: " + newTotalPrice);
        }, 20, TimeUnit.SECONDS);
    }
}
