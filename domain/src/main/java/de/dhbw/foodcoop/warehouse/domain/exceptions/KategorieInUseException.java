package de.dhbw.foodcoop.warehouse.domain.exceptions;

public class KategorieInUseException extends RuntimeException {
    public KategorieInUseException(String id) {
        super("The Kategorie " + id + " is used by at least one Produkt.");
    }
}
