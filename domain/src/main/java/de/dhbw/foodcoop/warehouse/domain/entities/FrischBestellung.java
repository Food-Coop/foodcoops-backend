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
    @JoinColumn(name = "frischbestand_id")
    private FrischBestand frischbestand;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    @Column
    private Date date;

    public FrischBestellung(String id, FrischBestand frischbestand, Person person, Date date) {
        Validate.notBlank(id);
        Validate.notNull(date);
        this.id = id;
        this.frischbestand = frischbestand;
        this.person = person;
        this.date = date;
    }

    public FrischBestellung(FrischBestand frischbestand, Person person, Date date) {
        this(UUID.randomUUID().toString(), frischbestand, person, date);
    }

    public FrischBestellung() {

    }

    public String getId() {
        return id;
    }

    public FrischBestand getFrischbestand() {
        return frischbestand;
    }

    public void setFrischbestand(FrischBestand frischbestand) {
        this.frischbestand = frischbestand;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
