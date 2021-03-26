package de.dhbw.foodcoop.warehouse.plugins.rest.exceptions;

public class ProduktNotFoundException extends RuntimeException {
    public ProduktNotFoundException(String id) {
        super("Could not find Produkt " + id);
    }
}
