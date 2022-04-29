package de.dhbw.foodcoop.warehouse.domain.exceptions;

public class DeadlineInUseException extends RuntimeException{
    public DeadlineInUseException(String id) {
        super("The Deadline " + id + " is used by at least one Entity.");
    }
}
