package de.dhbw.foodcoop.warehouse.domain.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name="frischbestellung")
public class FrischBestellung {
    @Id
    private String id;
    @Column
    private String person_id;
    @ManyToOne
    @JoinColumn(name = "frischbestand_id")
    private FrischBestand frischbestand;
    @Column
    private long bestellmenge;
    @Column
    private Timestamp datum;

    public FrischBestellung(String id, String person_id, FrischBestand frischbestand, long bestellmenge, Timestamp datum) {
        //Validate.notBlank(id);
        //Validate.notNull(datum);
        this.id = id;
        this.person_id = person_id;
        this.frischbestand = frischbestand;
        this.bestellmenge = bestellmenge;
        this.datum = datum;
    }

    public FrischBestellung(String id, String person_id, FrischBestand frischbestand, long bestellmenge) {
        //Validate.notBlank(id);
        //Validate.notNull(datum);
        this.id = id;
        this.person_id = person_id;
        this.frischbestand = frischbestand;
        this.bestellmenge = bestellmenge;
    }

    public FrischBestellung(String person_id, FrischBestand frischbestand, long bestellmenge, Timestamp datum) {
        this(UUID.randomUUID().toString(), person_id, frischbestand, bestellmenge, datum);
    }

    public FrischBestellung(String person_id, FrischBestand frischbestand, long bestellmenge) {
        this(UUID.randomUUID().toString(), person_id, frischbestand, bestellmenge);
    }

    public FrischBestellung() {

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

    public FrischBestand getFrischbestand(){
        return frischbestand;
    }

    public void setFrischbestand(FrischBestand frischbestand){
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
