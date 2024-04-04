package de.dhbw.foodcoop.warehouse.adapters.representations;

import java.time.LocalDateTime;
import java.util.Set;

public class EinkaufRepresentation {
	private String id;
	private String personId;
	Set<BestellungBuyRepresentation> bestellungsEinkauf;
	Set<BestandBuyRepresentation> bestandEinkauf;
	private LocalDateTime date;
	private double breadPriceAtTime;
	private double freshPriceAtTime;
	private double bestandPriceAtTime;
	public EinkaufRepresentation(String id, String personId, Set<BestellungBuyRepresentation> bestellungsEinkauf,
			Set<BestandBuyRepresentation> bestandEinkauf, LocalDateTime date, double breadPriceAtTime,
			double freshPriceAtTime, double bestandPriceAtTime) {
		super();
		this.id = id;
		this.personId = personId;
		this.bestellungsEinkauf = bestellungsEinkauf;
		this.bestandEinkauf = bestandEinkauf;
		this.date = date;
		this.breadPriceAtTime = breadPriceAtTime;
		this.freshPriceAtTime = freshPriceAtTime;
		this.bestandPriceAtTime = bestandPriceAtTime;
	}
	public String getId() {
		return id;
	}
	public String getPersonId() {
		return personId;
	}
	public Set<BestellungBuyRepresentation> getBestellungsEinkauf() {
		return bestellungsEinkauf;
	}
	public Set<BestandBuyRepresentation> getBestandEinkauf() {
		return bestandEinkauf;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public double getBreadPriceAtTime() {
		return breadPriceAtTime;
	}
	public double getFreshPriceAtTime() {
		return freshPriceAtTime;
	}
	public double getBestandPriceAtTime() {
		return bestandPriceAtTime;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
