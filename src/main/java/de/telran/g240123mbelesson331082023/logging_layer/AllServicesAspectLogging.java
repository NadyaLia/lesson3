package de.telran.g240123mbelesson331082023.logging_layer;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AllServicesAspectLogging {
    private static final Logger logger = LoggerFactory.getLogger(AllServicesAspectLogging.class);

    @Pointcut("execution(* de.telran.g240123mbelesson331082023.service.jpa.JpaProductService.*(..)) || " +
            "execution(* de.telran.g240123mbelesson331082023.service.jpa.JpaCustomerService.*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            logger.info(String.format("Method %s of class %s is called with arguments %s",
                    joinPoint.getSignature().getName(), joinPoint.getTarget().getClass().getName(), args[0]));
        } else {
            logger.info(String.format("Method %s of class %s is called with no arguments",
                    joinPoint.getSignature().getName(), joinPoint.getTarget().getClass().getName()));
        }
    }

    @After("serviceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        logger.info(String.format("Method %s of class %s has finished execution",
                joinPoint.getSignature().getName(), joinPoint.getTarget().getClass().getName()));
    }

    @AfterReturning(value = "serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info(String.format("Method %s of class %s successfully returned %s",
                joinPoint.getSignature().getName(), joinPoint.getTarget().getClass().getName(), result));
    }

    @AfterThrowing(value = "serviceMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error(String.format("Method %s of class %s has thrown an exception: %s",
                joinPoint.getSignature().getName(), joinPoint.getTarget().getClass().getName(), exception.getMessage()));
    }
}
