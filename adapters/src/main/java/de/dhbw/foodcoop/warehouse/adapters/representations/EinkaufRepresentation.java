package de.dhbw.foodcoop.warehouse.adapters.representations;

import java.sql.Timestamp;
import java.util.Set;

import de.dhbw.foodcoop.warehouse.domain.entities.BestandBuyEntity;

public class EinkaufRepresentation {
	private String id;
	private String personId;
	Set<BestellungRepresentation> bestellungsEinkauf;
	Set<BestandBuyRepresentation> bestandEinkauf;
	private Timestamp date;
	private double breadPriceAtTime;
	private double freshPriceAtTime;
	private double bestandPriceAtTime;
	public EinkaufRepresentation(String id, String personId, Set<BestellungRepresentation> bestellungsEinkauf,
			Set<BestandBuyRepresentation> bestandEinkauf, Timestamp date, double breadPriceAtTime,
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
	public Set<BestellungRepresentation> getBestellungsEinkauf() {
		return bestellungsEinkauf;
	}
	public Set<BestandBuyRepresentation> getBestandEinkauf() {
		return bestandEinkauf;
	}
	public Timestamp getDate() {
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
	
	
}
