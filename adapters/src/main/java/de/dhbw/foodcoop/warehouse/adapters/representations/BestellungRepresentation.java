package de.dhbw.foodcoop.warehouse.adapters.representations;

import java.sql.Timestamp;

public class BestellungRepresentation {

	protected String id;
	private String personId;
	private Timestamp datum;
	private double bestellmenge;
	private boolean isDone;
	private double reeleMenge;
	public BestellungRepresentation(String id, String personId, Timestamp datum, double bestellmenge, boolean isDone,
			double reeleMenge) {
		super();
		this.id = id;
		this.personId = personId;
		this.datum = datum;
		this.bestellmenge = bestellmenge;
		this.isDone = isDone;
		this.reeleMenge = reeleMenge;
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
	public double getReeleMenge() {
		return reeleMenge;
	}
	
	
}
