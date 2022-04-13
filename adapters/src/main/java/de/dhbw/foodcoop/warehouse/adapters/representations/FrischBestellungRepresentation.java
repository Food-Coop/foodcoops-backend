package de.dhbw.foodcoop.warehouse.adapters.representations;

import java.sql.Timestamp;

public class FrischBestellungRepresentation {
    private String id;
    private String person_id;
    private String frischbestand_id;
    private int bestellmenge;
    private Timestamp datum;

    public FrischBestellungRepresentation(String id, String person_id, String frischbestand_id, int bestellmenge, Timestamp datum) {
        this.id = id;
        this.person_id = person_id;
        this.frischbestand_id = frischbestand_id;
        this.bestellmenge = bestellmenge;
        this.datum = datum;
    }

    public FrischBestellungRepresentation(String id, String person_id, String frischbestand_id, int bestellmenge) {
        this.id = id;
        this.person_id = person_id;
        this.frischbestand_id = frischbestand_id;
        this.bestellmenge = bestellmenge;
    }

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

    public Timestamp getDatum(){
        return datum;
    }

    public void setDatum(Timestamp datum){
        this.datum = datum;
    }

}
