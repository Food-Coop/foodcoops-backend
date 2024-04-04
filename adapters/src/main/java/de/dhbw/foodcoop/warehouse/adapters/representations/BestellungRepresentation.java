package de.dhbw.foodcoop.warehouse.adapters.representations;

import java.time.LocalDateTime;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
	private LocalDateTime datum;
	private double bestellmenge;
	private boolean isDone;
	public BestellungRepresentation(String id, String personId, LocalDateTime datum, double bestellmenge, boolean isDone
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
	public LocalDateTime getDatum() {
		return datum;
	}
	public double getBestellmenge() {
		return bestellmenge;
	}
	public boolean isDone() {
		return isDone;
	}
	
	
}
