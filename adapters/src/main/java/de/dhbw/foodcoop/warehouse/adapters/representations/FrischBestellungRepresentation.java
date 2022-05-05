package de.dhbw.foodcoop.warehouse.adapters.representations;

import java.sql.Timestamp;

public class FrischBestellungRepresentation {
    private String id;
    private String person_id;
    private FrischBestandRepresentation frischbestand;
    private long bestellmenge;
    private Timestamp datum;

    public FrischBestellungRepresentation(String id, String person_id, FrischBestandRepresentation frischbestand, long bestellmenge, Timestamp datum) {
        this.id = id;
        this.person_id = person_id;
        this.frischbestand = frischbestand;
        this.bestellmenge = bestellmenge;
        this.datum = datum;
    }

//    public FrischBestellungRepresentation(String id, String person_id, FrischBestandRepresentation frischbestand, long bestellmenge) {
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

    public long getBestellmenge(){
        return bestellmenge;
    }

    public void setBestellmenge(long bestellmenge){
        this.bestellmenge = bestellmenge;
    }

    public Timestamp getDatum(){
        return datum;
    }

    public void setDatum(Timestamp datum){
        this.datum = datum;
    }

}
