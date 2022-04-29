package de.dhbw.foodcoop.warehouse.domain.exceptions;

public class BrotBestandNotFoundException extends RuntimeException{
    public BrotBestandNotFoundException(String id) {
        super("Could not find BrotBestand " + id);
    }
}
