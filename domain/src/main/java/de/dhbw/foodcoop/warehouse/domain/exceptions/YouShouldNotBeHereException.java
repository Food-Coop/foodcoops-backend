package de.dhbw.foodcoop.warehouse.domain.exceptions;

public class YouShouldNotBeHereException extends RuntimeException {
    public YouShouldNotBeHereException() {
        super("This state should not be reached.");
    }
}
