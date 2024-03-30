package de.dhbw.foodcoop.warehouse.plugins.helpObjects;

import de.dhbw.foodcoop.warehouse.domain.entities.BestandEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;

public class BestandBuyCreator {

	private Produkt bestandEntity;
	private double amount;
	
	
	
	
	public Produkt getBestandEntity() {
		return bestandEntity;
	}
	public double getAmount() {
		return amount;
	}
	public void setBestandEntity(Produkt bestandEntity) {
		this.bestandEntity = bestandEntity;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}
