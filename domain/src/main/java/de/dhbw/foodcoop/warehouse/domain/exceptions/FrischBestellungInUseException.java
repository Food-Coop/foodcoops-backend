package de.dhbw.foodcoop.warehouse.domain.exceptions;

public class FrischBestellungInUseException extends RuntimeException{
    public FrischBestellungInUseException(String id) {
        super("The FrischBestellung " + id + " is used by at least one Entity.");
    }
}
