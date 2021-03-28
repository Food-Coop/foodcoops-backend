package de.dhbw.foodcoop.warehouse.domain.repositories.exceptions;

public class KategorieNotFoundException extends RuntimeException {
    public KategorieNotFoundException(String id) {
        super("Could not find Kategorie " + id);
    }
}
