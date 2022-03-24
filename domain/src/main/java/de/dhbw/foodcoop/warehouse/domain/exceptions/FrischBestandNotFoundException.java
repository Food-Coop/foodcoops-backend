package de.dhbw.foodcoop.warehouse.domain.exceptions;

public class FrischBestandNotFoundException extends RuntimeException{
    public FrischBestandNotFoundException(String id) {
        super("Could not find FrischBestand " + id);
    }
}
