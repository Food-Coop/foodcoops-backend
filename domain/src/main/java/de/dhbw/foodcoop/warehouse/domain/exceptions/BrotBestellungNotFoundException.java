package de.dhbw.foodcoop.warehouse.domain.exceptions;

public class BrotBestellungNotFoundException extends RuntimeException{
    public BrotBestellungNotFoundException(String id) {
        super("Could not find BrotBestellung " + id);
    }
}
