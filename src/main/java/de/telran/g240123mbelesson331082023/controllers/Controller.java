package de.telran.g240123mbelesson331082023.controllers;

import de.telran.g240123mbelesson331082023.exception_layer.Response;
import de.telran.g240123mbelesson331082023.exception_layer.exceptions.FirstTestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface Controller {

    // 1 способ обработки ошибок средствами Spring
    // Такой метод нужно прописывать в каждом контроллере
    // или сервисе, либо в супер-классе или интерфейсе,
    // от которого наследуются все контроллеры.
    // Но такой способ не всегда возможен, если у нас
    // уже сложная структура контроллеров, и заранее это не было предусмотрено
    @ExceptionHandler(FirstTestException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    default Response handleException(FirstTestException e) {
        return new Response(e.getMessage());
    }
}
