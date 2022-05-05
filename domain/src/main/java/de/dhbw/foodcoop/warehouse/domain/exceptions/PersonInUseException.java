package de.dhbw.foodcoop.warehouse.domain.exceptions;

public class PersonInUseException extends RuntimeException{
    public PersonInUseException(String id) {
        super("The Person " + id + " is used by at least one Frischbestellung or Brotbestellung.");
    } 
}
