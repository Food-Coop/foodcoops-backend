package de.dhbw.foodcoop.warehouse.domain.exceptions;

public class ProduktNotFoundException extends RuntimeException {
    public ProduktNotFoundException(String id) {
        super("Could not find Produkt " + id);
    }
}
