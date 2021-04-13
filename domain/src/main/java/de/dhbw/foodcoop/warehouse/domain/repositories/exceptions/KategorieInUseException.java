package de.dhbw.foodcoop.warehouse.domain.repositories.exceptions;

public class KategorieInUseException extends Throwable {
    public KategorieInUseException(String id) {
        super("The Kategorie " + id + " is used by at least one Lagerbestand.");
    }
}
