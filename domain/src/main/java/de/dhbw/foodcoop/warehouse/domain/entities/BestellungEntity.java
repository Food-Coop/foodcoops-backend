package de.dhbw.foodcoop.warehouse.domain.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = FrischBestellung.class, name = "frisch"),
    @JsonSubTypes.Type(value = BrotBestellung.class, name ="brot")
    // Andere Subtypen hier
})
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "bestellung")
public abstract class BestellungEntity {

	
	@Id
	protected String id;
	
	@Column
	protected String personId;
	
	@Column
	protected Timestamp datum;
	
	@Column
	protected double bestellmenge;
	
	@Column
	protected boolean isDone;
	
	@Column
	private double reeleMenge;
	

 public BestellungEntity() {

 }
	
	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
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

	public Timestamp getDatum() {
		return datum;
	}

	
	public double getReeleMenge() {
		return reeleMenge;
	}

	public void setReeleMenge(double reeleMenge) {
		this.reeleMenge = reeleMenge;
	}

	public void setDatum(Timestamp datum) {
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
