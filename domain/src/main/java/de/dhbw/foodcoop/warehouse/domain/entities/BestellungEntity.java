package de.dhbw.foodcoop.warehouse.domain.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import de.dhbw.foodcoop.warehouse.domain.shopping.Buyable;


@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = FrischBestellung.class, name = "frisch"),
    @JsonSubTypes.Type(value = BrotBestellung.class, name ="brot")
    // Andere Subtypen hier
})
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "bestellung")
public abstract class BestellungEntity implements Buyable {

	
	@Id
	protected String id;
	
	@Column
	protected String personId;
	
	@Column
	protected LocalDateTime datum;
	
	@Column
	protected double bestellmenge;
	
	@Column
	protected boolean done;
	
	

	public BestellungEntity() {
	
	}
	
	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public LocalDateTime getDatum() {
		return datum;
	}


	public void setDatum(LocalDateTime datum) {
		this.datum = datum;
	}
	
    public double getBestellmenge(){
        return bestellmenge;
    }

    public void setBestellmenge(double bestellmenge){
        this.bestellmenge = bestellmenge;
    }

//	public void setEinkauf(EinkaufEntity einkauf) {
//		this.einkauf = einkauf;
//		// TODO Auto-generated method stub
//		
//	}
//	
//	public EinkaufEntity getEinkauf() {
//		return this.einkauf;
//	}
	
}
