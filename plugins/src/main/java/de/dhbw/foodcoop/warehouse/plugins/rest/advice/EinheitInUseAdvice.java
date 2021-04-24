package de.dhbw.foodcoop.warehouse.plugins.rest.advice;

import de.dhbw.foodcoop.warehouse.domain.exceptions.EinheitInUseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EinheitInUseAdvice {
    @ResponseBody
    @ExceptionHandler(EinheitInUseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEinheitInUse(EinheitInUseException exception) {
        return exception.getMessage();
    }
}
