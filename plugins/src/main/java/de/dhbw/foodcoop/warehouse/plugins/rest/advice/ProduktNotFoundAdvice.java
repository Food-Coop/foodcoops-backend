package de.dhbw.foodcoop.warehouse.plugins.rest.advice;

import de.dhbw.foodcoop.warehouse.domain.exceptions.ProduktNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProduktNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(ProduktNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleProduktNotFound(ProduktNotFoundException exception) {
        return exception.getMessage();
    }

}
