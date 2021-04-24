package de.dhbw.foodcoop.warehouse.plugins.rest.advice;

import de.dhbw.foodcoop.warehouse.domain.exceptions.KategorieInUseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class KategorieInUseAdvice {
    @ResponseBody
    @ExceptionHandler(KategorieInUseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleKategorieInUse(KategorieInUseException exception) {
        return exception.getMessage();
    }
}
