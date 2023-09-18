package de.telran.g240123mbelesson331082023.logging_layer;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProductServiceAspectLogging {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceAspectLogging.class);

    @Pointcut("execution(* de.telran.g240123mbelesson331082023.service.jpa.JpaProductService.*(..))")
    public void productServiceMethods() {}

    @Before("productServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            logger.info(String.format("Method %s is called with arguments %s",
                    joinPoint.getSignature().getName(), args[0]));
        } else {
            logger.info(String.format("Method %s is called with no arguments", joinPoint.getSignature().getName()));
        }
    }

    @After("productServiceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        logger.info(String.format("Method %s has finished execution", joinPoint.getSignature().getName()));
    }
}
