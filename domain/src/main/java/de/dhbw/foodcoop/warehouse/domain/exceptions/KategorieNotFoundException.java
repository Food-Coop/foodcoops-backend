package de.dhbw.foodcoop.warehouse.domain.exceptions;

public class KategorieNotFoundException extends RuntimeException {
    public KategorieNotFoundException(String id) {
        super("Could not find Kategorie " + id);
    }
}
