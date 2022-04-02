package de.dhbw.foodcoop.warehouse.adapters.representations;

import java.sql.Date;

public class FrischBestellungRepresentation {
    private String id;
    private PersonRepresentation person;
    private FrischBestandRepresentation frischbestand;
    private int bestellmenge;
    private Date datum;

    public FrischBestellungRepresentation(String id, PersonRepresentation person, FrischBestandRepresentation frischbestand, int bestellmenge, Date datum) {
        this.id = id;
        this.person = person;
        this.frischbestand = frischbestand;
        this.bestellmenge = bestellmenge;
        this.datum = datum;
    }

    public String getId(){
        return id;
    }

    public PersonRepresentation getPerson(){
        return person;
    }

    public void setPerson(PersonRepresentation person){
        this.person = person;
    }

    public FrischBestandRepresentation getFrischbestand(){
        return frischbestand;
    }

    public void setFrischbestand(FrischBestandRepresentation frischbestand){
        this.frischbestand = frischbestand;
    }

    public int getBestellmenge(){
        return bestellmenge;
    }

    public void setBestellmenge(int bestellmenge){
        this.bestellmenge = bestellmenge;
    }

    public Date getDatum(){
        return datum;
    }

    public void setDatum(Date datum){
        this.datum = datum;
    }

}
