package de.dhbw.foodcoop.warehouse.domain.repositories.exceptions;

public class EinheitNotFoundException extends RuntimeException {
    public EinheitNotFoundException(String id) {
        super("Could not find Produkt " + id);
    }
}
