package de.dhbw.foodcoop.warehouse.plugins.rest.advice;

import de.dhbw.foodcoop.warehouse.domain.exceptions.EinheitIsInUseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EinheitIsInUseAdvice {
    @ResponseBody
    @ExceptionHandler(EinheitIsInUseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String einheitIsInUseHandler(EinheitIsInUseException exception) {
        return exception.getMessage();
    }
}
