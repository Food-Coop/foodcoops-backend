package de.dhbw.foodcoop.warehouse.domain.repositories.exceptions;

public class ProduktIsInUseException extends Throwable {
    public ProduktIsInUseException(String id) {
        super("The Produkt " + id + " is still in stock in.");
    }
}
