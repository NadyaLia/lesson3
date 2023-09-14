package de.telran.g240123mbelesson331082023.exception_layer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 2 способ обработки ошибок средствами Spring
// Данная аннотация действует "глобально", то есть
// указанный статус ответа будет отправлен клиенту,
// если данная ошибка выброшена в любом месте нашего приложения
// Минус в том, что мы отправляем клиенту нужный код статуса,
// но не отправляем сам текст, из-за чего возникла ошибка

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SecondTestException extends RuntimeException {
    public SecondTestException(String message) {
        super(message);
    }
}
