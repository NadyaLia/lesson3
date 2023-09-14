package de.telran.g240123mbelesson331082023.exception_layer.exceptions;

public class CartIsEmptyException extends RuntimeException {
    public CartIsEmptyException(String message) {
        super(message);
    }
}
