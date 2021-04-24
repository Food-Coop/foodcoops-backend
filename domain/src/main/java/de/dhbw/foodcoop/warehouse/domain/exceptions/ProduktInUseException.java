package de.dhbw.foodcoop.warehouse.domain.exceptions;

public class ProduktInUseException extends RuntimeException {
    public ProduktInUseException(String id) {
        super("The Produkt " + id + " is still in stock.");
    }
}
