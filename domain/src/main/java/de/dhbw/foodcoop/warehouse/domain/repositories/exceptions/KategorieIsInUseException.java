package de.dhbw.foodcoop.warehouse.domain.repositories.exceptions;

public class KategorieIsInUseException extends RuntimeException {
    public KategorieIsInUseException(String id) {
        super("The Kategorie " + id + " is used by at least one Produkt.");
    }
}
