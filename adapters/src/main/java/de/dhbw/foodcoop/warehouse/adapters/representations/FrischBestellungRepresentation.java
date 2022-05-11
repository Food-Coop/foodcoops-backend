package de.dhbw.foodcoop.warehouse.adapters.representations;

import java.sql.Timestamp;

public class FrischBestellungRepresentation {
    private String id;
    private String person_id;
    private FrischBestandRepresentation frischbestand;
    private double bestellmenge;
    private Timestamp datum;

    public FrischBestellungRepresentation(String id, String person_id, FrischBestandRepresentation frischbestand, double bestellmenge, Timestamp datum) {
        this.id = id;
        this.person_id = person_id;
        this.frischbestand = frischbestand;
        this.bestellmenge = bestellmenge;
        this.datum = datum;
    }

//    public FrischBestellungRepresentation(String id, String person_id, FrischBestandRepresentation frischbestand, double bestellmenge) {
//        this.id = id;
//        this.person_id = person_id;
//        this.frischbestand = frischbestand;
//        this.bestellmenge = bestellmenge;
//    }


    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getPersonId(){
        return person_id;
    }

    public void setPersonId(String person_id){
        this.person_id = person_id;
    }

    public FrischBestandRepresentation getFrischbestand(){
        return frischbestand;
    }

    public void setFrischbestand(FrischBestandRepresentation frischbestand){
        this.frischbestand = frischbestand;
    }

    public double getBestellmenge(){
        return bestellmenge;
    }

    public void setBestellmenge(double bestellmenge){
        this.bestellmenge = bestellmenge;
    }

    public Timestamp getDatum(){
        return datum;
    }

    public void setDatum(Timestamp datum){
        this.datum = datum;
    }

}
