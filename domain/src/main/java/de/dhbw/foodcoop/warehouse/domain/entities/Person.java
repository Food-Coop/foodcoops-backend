package de.dhbw.foodcoop.warehouse.domain.entities;

import org.apache.commons.lang3.Validate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "person")
public class Person {
    @Id
    private String id;
    @Column
    private String vorname;
    @Column
    private String nachname;

    public Person(String id, String vorname, String nachname) {
        Validate.notBlank(id);
        Validate.notBlank(vorname);
        Validate.notBlank(nachname);
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public Person(String vorname, String nachname) {
        this(UUID.randomUUID().toString(), vorname, nachname);
    }

    public Person() {

    }

    public String getId() {
        return id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }
}
