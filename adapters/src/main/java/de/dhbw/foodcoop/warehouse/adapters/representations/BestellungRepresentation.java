package de.dhbw.foodcoop.warehouse.adapters.representations;

import java.sql.Timestamp;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;

//Parent class
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = FrischBestellungRepresentation.class, name = "frisch"),
    @JsonSubTypes.Type(value = BrotBestellungRepresentation.class, name ="brot")
    // Andere Subtypen hier
})
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BestellungRepresentation {

	protected String id;
	private String personId;
	private Timestamp datum;
	private double bestellmenge;
	private boolean isDone;
	public BestellungRepresentation(String id, String personId, Timestamp datum, double bestellmenge, boolean isDone
		) {
		super();
		this.id = id;
		this.personId = personId;
		this.datum = datum;
		this.bestellmenge = bestellmenge;
		this.isDone = isDone;
	}
	
	public String getId() {
		return id;
	}
	public String getPersonId() {
		return personId;
	}
	public Timestamp getDatum() {
		return datum;
	}
	public double getBestellmenge() {
		return bestellmenge;
	}
	public boolean isDone() {
		return isDone;
	}
	
	
}
