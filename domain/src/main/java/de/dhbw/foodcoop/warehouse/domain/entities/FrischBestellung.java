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
   // @ManyToOne
    @Column(name = "person_id")
    //private Person person;
    private String person_id;
   // @ManyToOne
    @Column(name = "frischbestand_id")
    //private FrischBestand frischbestand;
    private String frischbestand_id;
    @Column
    private int bestellmenge;
    //@Column
    //private double preis;
    @Column
    private Date datum;

    // public FrischBestellung(String id, Person person, FrischBestand frischbestand, int bestellmenge, Date datum) {
    //     Validate.notBlank(id);
    //     Validate.notNull(datum);
    //     this.id = id;
    //     this.person = person;
    //     this.frischbestand = frischbestand;
    //     this.bestellmenge = bestellmenge;
    //     this.datum = datum;
    // }

    public FrischBestellung(String id, String person_id, String frischbestand_id, int bestellmenge, Date datum) {
        Validate.notBlank(id);
        Validate.notNull(datum);
        this.id = id;
        this.person_id = person_id;
        this.frischbestand_id = frischbestand_id;
        this.bestellmenge = bestellmenge;
        this.datum = datum;
    }

    // public FrischBestellung(Person person, FrischBestand frischbestand, int bestellmenge, Date datum) {
    //     this(UUID.randomUUID().toString(), person, frischbestand, bestellmenge, datum);
    // }

    public FrischBestellung(String person_id, String frischbestand_id, int bestellmenge, Date datum) {
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

    // public Person getPerson(){
    //     return person;
    // }

    // public void setPerson(Person person){
    //     this.person = person;
    // }

    public String getPersonId(){
        return person_id;
    }

    public void setPersonId(String person_id){
        this.person_id = person_id;
    }

    // public FrischBestand getFrischbestand(){
    //     return frischbestand;
    // }

    // public void setFrischbestand(FrischBestand frischbestand){
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
