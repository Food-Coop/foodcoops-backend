package de.dhbw.foodcoop.warehouse.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="frischbestellung")
public class FrischBestellung extends BestellungEntity{

    @ManyToOne
    @JoinColumn(name = "frischbestand_id")
    private FrischBestand frischbestand;
    
 

    public FrischBestellung(String id, String personId, FrischBestand frischbestand, double bestellmenge, LocalDateTime datum) {
        //Validate.notBlank(id);
        //Validate.notNull(datum);
    	super();
        this.id = id;
        this.personId = personId;
        this.frischbestand = frischbestand;
        this.bestellmenge = bestellmenge;
        this.datum = datum;
    }

    public FrischBestellung(String id, String personId, FrischBestand frischbestand, double bestellmenge) {
        //Validate.notBlank(id);
        //Validate.notNull(datum);
    	super();
        this.id = id;
        this.personId = personId;
        this.frischbestand = frischbestand;
        this.bestellmenge = bestellmenge;
    }

    public FrischBestellung(String personId, FrischBestand frischbestand, double bestellmenge, LocalDateTime datum) {
        this(UUID.randomUUID().toString(), personId, frischbestand, bestellmenge, datum);
    }

    public FrischBestellung(String personId, FrischBestand frischbestand, double bestellmenge) {
        this(UUID.randomUUID().toString(), personId, frischbestand, bestellmenge);
    }

    public FrischBestellung() {
    	super();
    }

   
    public FrischBestand getFrischbestand(){
        return frischbestand;
    }

    public void setFrischbestand(FrischBestand frischbestand){
        this.frischbestand = frischbestand;
    }


}
