package de.dhbw.foodcoop.warehouse.domain.entities;

import org.apache.commons.lang3.Validate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name="frischbestellung")
public class FrischBestellung {
    @Id
    private String id;
    @Column(name = "person_id")
    private String person_id;
    @Column(name = "frischbestand_id")
    private String frischbestand_id;
    @Column
    private int bestellmenge;
    @Column
    private Timestamp datum;

    public FrischBestellung(String id, String person_id, String frischbestand_id, int bestellmenge, Timestamp datum) {
        Validate.notBlank(id);
        Validate.notNull(datum);
        this.id = id;
        this.person_id = person_id;
        this.frischbestand_id = frischbestand_id;
        this.bestellmenge = bestellmenge;
        this.datum = datum;
    }

    public FrischBestellung(String person_id, String frischbestand_id, int bestellmenge, Timestamp datum) {
        this(UUID.randomUUID().toString(), person_id, frischbestand_id, bestellmenge, datum);
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
