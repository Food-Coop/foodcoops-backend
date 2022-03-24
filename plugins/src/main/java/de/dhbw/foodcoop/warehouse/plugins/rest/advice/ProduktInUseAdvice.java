package de.dhbw.foodcoop.warehouse.plugins.rest.advice;

import de.dhbw.foodcoop.warehouse.domain.exceptions.ProduktInUseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProduktInUseAdvice {
    @ResponseBody
    @ExceptionHandler(ProduktInUseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleProduktInUse(ProduktInUseException exception) {
        return exception.getMessage();
    }
}
