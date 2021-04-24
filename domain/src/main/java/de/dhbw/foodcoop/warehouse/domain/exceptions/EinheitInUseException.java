package de.dhbw.foodcoop.warehouse.domain.exceptions;

public class EinheitInUseException extends RuntimeException {
    public EinheitInUseException(String id) {
        super("The Einheit " + id + " is used by at least one Produkt.");
    }
}
