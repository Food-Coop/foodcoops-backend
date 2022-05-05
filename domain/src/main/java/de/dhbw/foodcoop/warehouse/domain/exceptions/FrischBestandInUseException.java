package de.dhbw.foodcoop.warehouse.domain.exceptions;

public class FrischBestandInUseException extends RuntimeException{

    public FrischBestandInUseException(String id) {
        super("The FrischBestand " + id + " is used by at least one FrischBestellung.");
    }

}
