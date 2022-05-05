package de.dhbw.foodcoop.warehouse.domain.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name="brotbestellung")
public class BrotBestellung {
    @Id
    private String id;
    @Column
    private String person_id;
    @ManyToOne
    @JoinColumn(name = "brotbestand_id")
    private BrotBestand brotbestand;
    @Column
    private long bestellmenge;
    @Column
    private Timestamp datum;

    public  BrotBestellung(String id, String person_id, BrotBestand brotbestand, long bestellmenge, Timestamp datum) {
        this.id = id;
        this.person_id = person_id;
        this.brotbestand = brotbestand;
        this.bestellmenge = bestellmenge;
        this.datum = datum;
    }

    public BrotBestellung(String id, String person_id, BrotBestand brotbestand, long bestellmenge) {
        //Validate.notBlank(id);
        //Validate.notNull(datum);
        this.id = id;
        this.person_id = person_id;
        this.brotbestand = brotbestand;
        this.bestellmenge = bestellmenge;
    }

    public BrotBestellung(String person_id, BrotBestand brotbestand, long bestellmenge, Timestamp datum) {
        this(UUID.randomUUID().toString(), person_id, brotbestand, bestellmenge, datum);
    }

    public BrotBestellung(String person_id, BrotBestand brotbestand, long bestellmenge) {
        this(UUID.randomUUID().toString(), person_id, brotbestand, bestellmenge);
    }

    public BrotBestellung() {

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

    public BrotBestand getBrotBestand(){
        return brotbestand;
    }

    public void setBrotBestand(BrotBestand brotbestand){
        this.brotbestand = brotbestand;
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
