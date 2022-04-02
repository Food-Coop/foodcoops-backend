package de.dhbw.foodcoop.warehouse.domain.entities;

import org.apache.commons.lang3.Validate;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name="frischbestellung")
public class FrischBestellung {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    @ManyToOne
    @JoinColumn(name = "frischbestand_id")
    private FrischBestand frischbestand;
    @Column
    private int bestellmenge;
    @Column
    private Date datum;

    public FrischBestellung(String id, Person person, FrischBestand frischbestand, int bestellmenge, Date datum) {
        Validate.notBlank(id);
        Validate.notNull(datum);
        this.id = id;
        this.person = person;
        this.frischbestand = frischbestand;
        this.bestellmenge = bestellmenge;
        this.datum = datum;
    }

    public FrischBestellung(Person person, FrischBestand frischbestand, int bestellmenge, Date datum) {
        this(UUID.randomUUID().toString(), person, frischbestand, bestellmenge, datum);
    }

    public FrischBestellung() {

    }

    public String getId(){
        return id;
    }

    public Person getPerson(){
        return person;
    }

    public void setPerson(Person person){
        this.person = person;
    }

    public FrischBestand getFrischbestand(){
        return frischbestand;
    }

    public void setFrischbestand(FrischBestand frischbestand){
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
