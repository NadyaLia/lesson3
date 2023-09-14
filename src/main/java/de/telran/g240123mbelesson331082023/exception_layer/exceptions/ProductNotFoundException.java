package de.telran.g240123mbelesson331082023.exception_layer.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
