package de.dhbw.foodcoop.warehouse.adapters.representations;

import java.sql.Date;

public class FrischBestellungRepresentation {
    private String id;
    private FrischBestandRepresentation frischbestand;
    private PersonRepresentation person;
    private Date date;

    public FrischBestellungRepresentation(String id, FrischBestandRepresentation frischbestand, PersonRepresentation person, Date date) {
        this.id = id;
        this.frischbestand = frischbestand;
        this.person = person;
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public FrischBestandRepresentation getFrischbestand() {
        return frischbestand;
    }

    public PersonRepresentation getPerson() {
        return person;
    }

    public Date getDate() {
        return date;
    }

}
