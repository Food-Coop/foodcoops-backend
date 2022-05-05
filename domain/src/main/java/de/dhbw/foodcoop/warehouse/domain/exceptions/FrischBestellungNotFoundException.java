package de.dhbw.foodcoop.warehouse.domain.exceptions;

public class FrischBestellungNotFoundException extends RuntimeException{
    public FrischBestellungNotFoundException(String id) {
        super("Could not find FrischBestellung " + id);
    }
}
