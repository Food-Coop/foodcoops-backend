package de.dhbw.foodcoop.warehouse.adapters.representations;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import de.dhbw.foodcoop.warehouse.domain.entities.BestandEntity;

public class DiscrepancyRepresentation {
	
	private String id;
	private BestandRepresentation bestand;
	private int zuBestellendeGebinde;
	private float zuVielzuWenig;
	private float gewollteMenge;
	public DiscrepancyRepresentation(String id, BestandRepresentation bestand, int zuBestellendeGebinde,
			float zuVielzuWenig, float gewollteMenge) {
		super();
		this.id = id;
		this.bestand = bestand;
		this.zuBestellendeGebinde = zuBestellendeGebinde;
		this.zuVielzuWenig = zuVielzuWenig;
		this.gewollteMenge = gewollteMenge;
	}
	public String getId() {
		return id;
	}
	public BestandRepresentation getBestand() {
		return bestand;
	}
	public int getZuBestellendeGebinde() {
		return zuBestellendeGebinde;
	}
	public float getZuVielzuWenig() {
		return zuVielzuWenig;
	}
	public float getGewollteMenge() {
		return gewollteMenge;
	}

}
