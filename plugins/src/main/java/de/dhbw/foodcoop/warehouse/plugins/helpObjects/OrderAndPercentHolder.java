package de.dhbw.foodcoop.warehouse.plugins.helpObjects;

import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;

public class OrderAndPercentHolder {

	private int percentage;
	
	private FrischBestellung bestellung;

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	public FrischBestellung getBestellung() {
		return bestellung;
	}

	public void setBestellung(FrischBestellung bestellung) {
		this.bestellung = bestellung;
	}

	public OrderAndPercentHolder(int percentage, FrischBestellung bestellung) {
		super();
		this.percentage = percentage;
		this.bestellung = bestellung;
	}

	public OrderAndPercentHolder() {
		// TODO Auto-generated constructor stub
	}

}
