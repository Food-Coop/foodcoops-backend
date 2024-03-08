package de.dhbw.foodcoop.warehouse.domain.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name="brotbestellung")
public class BrotBestellung extends BestellungEntity{


    @ManyToOne
    @JoinColumn(name = "brotbestand_id")
    private BrotBestand brotbestand;



    public  BrotBestellung(String id, String personId, BrotBestand brotbestand, double bestellmenge, Timestamp datum) {
    	super();
        this.id = id;
        this.personId = personId;
        this.brotbestand = brotbestand;
        this.bestellmenge = bestellmenge;
        this.datum = datum;
    }

    public BrotBestellung(String id, String personId, BrotBestand brotbestand, double bestellmenge) {
        //Validate.notBlank(id);
        //Validate.notNull(datum);
    	super();
        this.id = id;
        this.personId = personId;
        this.brotbestand = brotbestand;
        this.bestellmenge = bestellmenge;
    }

    public BrotBestellung(String personId, BrotBestand brotbestand, double bestellmenge, Timestamp datum) {
        this(UUID.randomUUID().toString(), personId, brotbestand, bestellmenge, datum);
    }

    public BrotBestellung(String personId, BrotBestand brotbestand, double bestellmenge) {
        this(UUID.randomUUID().toString(), personId, brotbestand, bestellmenge);
    }

    public BrotBestellung() {
    	super();
    }


    public BrotBestand getBrotBestand(){
        return brotbestand;
    }

    public void setBrotBestand(BrotBestand brotbestand){
        this.brotbestand = brotbestand;
    }




}
