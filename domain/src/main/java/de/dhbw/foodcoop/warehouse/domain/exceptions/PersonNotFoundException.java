package de.dhbw.foodcoop.warehouse.domain.exceptions;

public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException(String id) {
        super("Could not find Person " + id);
    }
}
