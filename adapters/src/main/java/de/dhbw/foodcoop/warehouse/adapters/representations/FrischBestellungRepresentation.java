package de.dhbw.foodcoop.warehouse.adapters.representations;

import java.sql.Date;

public class FrischBestellungRepresentation {
    private String id;
    //private PersonRepresentation person;
    private String person_id;
    //private FrischBestandRepresentation frischbestand;
    private String frischbestand_id;
    private int bestellmenge;
    //private double preis;
    private Date datum;

    // public FrischBestellungRepresentation(String id, PersonRepresentation person, FrischBestandRepresentation frischbestand, int bestellmenge, Date datum) {
    //     this.id = id;
    //     this.person = person;
    //     this.frischbestand = frischbestand;
    //     this.bestellmenge = bestellmenge;
    //     this.datum = datum;
    // }

    public FrischBestellungRepresentation(String id, String person_id, String frischbestand_id, int bestellmenge, Date datum) {
        this.id = id;
        this.person_id = person_id;
        this.frischbestand_id = frischbestand_id;
        this.bestellmenge = bestellmenge;
        this.datum = datum;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
        }

    // public PersonRepresentation getPerson(){
    //     return person;
    // }

    // public void setPerson(PersonRepresentation person){
    //     this.person = person;
    // }

    public String getPersonId(){
        return person_id;
    }

    public void setPersonId(String person_id){
        this.person_id = person_id;
    }

    // public FrischBestandRepresentation getFrischbestand(){
    //     return frischbestand;
    // }

    // public void setFrischbestand(FrischBestandRepresentation frischbestand){
    //     this.frischbestand = frischbestand;
    // }

    public String getFrischbestandId(){
        return frischbestand_id;
    }

    public void setFrischbestandId(String frischbestand_id){
        this.frischbestand_id = frischbestand_id;
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
