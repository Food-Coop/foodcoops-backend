package de.dhbw.foodcoop.warehouse.domain.exceptions;

public class DeadlineNotFoundException extends RuntimeException{
    public DeadlineNotFoundException(String id) {
        super("Could not find Deadline " + id);
    }

	public DeadlineNotFoundException() {
		super("No Deadline exists!");
	}
}
