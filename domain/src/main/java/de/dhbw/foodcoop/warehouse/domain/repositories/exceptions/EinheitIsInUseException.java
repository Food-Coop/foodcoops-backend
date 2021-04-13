package de.dhbw.foodcoop.warehouse.domain.repositories.exceptions;

public class EinheitIsInUseException extends RuntimeException {
    public EinheitIsInUseException(String id) {
        super("The Einheit " + id + " is used by at least one Produkt.");
    }
}
