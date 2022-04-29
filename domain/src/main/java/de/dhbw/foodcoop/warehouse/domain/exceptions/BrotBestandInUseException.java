package de.dhbw.foodcoop.warehouse.domain.exceptions;

public class BrotBestandInUseException extends RuntimeException{
    public BrotBestandInUseException(String id) {
        super("The BrotBestand " + id + " is used by at least one Entity.");
    }
}
