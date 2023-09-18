package de.telran.g240123mbelesson331082023.logging_layer;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLogging {
    private Logger logger = LoggerFactory.getLogger(AspectLogging.class);
    @Pointcut("execution(* de.telran.g240123mbelesson331082023.service.jpa.JpaProductService.add(..))")
    public void addProduct() {}
    @Before("addProduct()")
    public void beforeAddingProduct(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        logger.info(String.format("Method add of JpaProductService class was called with parameter - %s", args[0]));
    }
    @Pointcut("execution(* de.telran.g240123mbelesson331082023.service.jpa.JpaProductService.test(..))")
    public void test() {}
    @Before("test()")
    public void beforeTest(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        logger.info(String.format("Method test of JpaProductService class was called with parameter - %s", args[0]));
    }
    @After("test()")
    public void afterTest(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        logger.info(String.format("Method test of JpaProductService class was finished, object - %s", args[0]));
    }
    @Pointcut("execution(* de.telran.g240123mbelesson331082023.service.jpa.JpaProductService.getById(..))")
    public void getProductById() {}
    @AfterReturning("getProductById()")
    public void afterProductReturning(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        logger.info(String.format("Method getById of JpaProductService successfully returned the product with ID %d", args[0]));
    }
    @AfterThrowing("getProductById()")
    public void afterThrowingWhileProductReturning(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        logger.info(String.format("Method getById of JpaProductService called the error! Not exist ID - %d", args[0]));
    }

    @Pointcut("execution(* de.telran.g240123mbelesson331082023.service.jpa.JpaProductService.getCount(..))")
    public void getProductsCount() {}

/* @Around("getProductsCount()")
    public Object aroundGetProductsCount(ProceedingJoinPoint joinPoint) {
        logger.info("Around getProductsCount()");
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }*/


    @Around("getProductsCount()")
    public Object aroundGetProductsCount(ProceedingJoinPoint joinPoint) {
        logger.info("Around getProductsCount()");
        try {
            logger.info("Current result - " + joinPoint.proceed());
            logger.info("Change the result and return -1");
            return new Integer(-1);
        } catch (Throwable e) {
            logger.error("Here is the error!");
            throw new RuntimeException(e);
        }
    }
}
