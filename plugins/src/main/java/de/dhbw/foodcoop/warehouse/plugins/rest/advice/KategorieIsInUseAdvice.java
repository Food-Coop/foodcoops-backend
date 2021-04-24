package de.dhbw.foodcoop.warehouse.plugins.rest.advice;

import de.dhbw.foodcoop.warehouse.domain.exceptions.KategorieIsInUseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class KategorieIsInUseAdvice {
    @ResponseBody
    @ExceptionHandler(KategorieIsInUseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String einheitIsInUseHandler(KategorieIsInUseException exception) {
        return exception.getMessage();
    }
}
