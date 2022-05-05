package de.dhbw.foodcoop.warehouse.domain.exceptions;

public class BrotBestellungInUseException extends RuntimeException{
    public BrotBestellungInUseException(String id) {
        super("The BrotBestellung " + id + " is used by at least one Entity.");
    }
}
