package de.dhbw.foodcoop.warehouse.domain.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name="frischbestellung")
public class BrotBestellung {
    @Id
    private String id;
    @Column(name = "person_id")
    private String person_id;
    @ManyToOne
    @JoinColumn(name = "brot_id")
    private BrotBestand brot;
    @Column
    private long bestellmenge;
    @Column
    private Timestamp datum;

    public  BrotBestellung(String id, String person_id, BrotBestand brot, long bestellmenge, Timestamp datum) {
        this.id = id;
        this.person_id = person_id;
        this.brot = brot;
        this.bestellmenge = bestellmenge;
        this.datum = datum;
    }

    public BrotBestellung(String id, String person_id, BrotBestand brot, long bestellmenge) {
        //Validate.notBlank(id);
        //Validate.notNull(datum);
        this.id = id;
        this.person_id = person_id;
        this.brot = brot;
        this.bestellmenge = bestellmenge;
    }

    public BrotBestellung(String person_id, BrotBestand brot, long bestellmenge, Timestamp datum) {
        this(UUID.randomUUID().toString(), person_id, brot, bestellmenge, datum);
    }

    public BrotBestellung(String person_id, BrotBestand brot, long bestellmenge) {
        this(UUID.randomUUID().toString(), person_id, brot, bestellmenge);
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
        return brot;
    }

    public void setBrotBestand(BrotBestand brot){
        this.brot = brot;
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
