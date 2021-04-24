package de.dhbw.foodcoop.warehouse.plugins.rest.advice;

import de.dhbw.foodcoop.warehouse.domain.exceptions.KategorieNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class KategorieNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(KategorieNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String einheitIsInUseHandler(KategorieNotFoundException exception) {
        return exception.getMessage();
    }
}
