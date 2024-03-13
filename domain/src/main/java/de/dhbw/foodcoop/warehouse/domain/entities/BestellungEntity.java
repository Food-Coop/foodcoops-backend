package de.dhbw.foodcoop.warehouse.domain.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.dhbw.foodcoop.warehouse.domain.values.EinkaufBestellungVergleich;


@Entity
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
	
	@OneToOne(mappedBy = "bestellung")
	private EinkaufBestellungVergleich ebv;
	

	
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

	public void setDatum(Timestamp datum) {
		this.datum = datum;
	}
	
    public double getBestellmenge(){
        return bestellmenge;
    }

    public void setBestellmenge(double bestellmenge){
        this.bestellmenge = bestellmenge;
    }
	
}
