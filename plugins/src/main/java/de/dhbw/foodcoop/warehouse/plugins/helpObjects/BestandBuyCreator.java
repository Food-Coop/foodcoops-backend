package de.dhbw.foodcoop.warehouse.plugins.helpObjects;

import de.dhbw.foodcoop.warehouse.domain.entities.BestandEntity;

public class BestandBuyCreator {

	private BestandEntity bestandEntity;
	private double amount;
	public BestandEntity getBestandEntity() {
		return bestandEntity;
	}
	public double getAmount() {
		return amount;
	}
	public void setBestandEntity(BestandEntity bestandEntity) {
		this.bestandEntity = bestandEntity;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}
