package de.dhbw.foodcoop.warehouse.plugins.helpObjects;

import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;

public class CategoryAndPercentHolder {

	private int percentage;
	
	private Kategorie kategorie;

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	public Kategorie getKategorie() {
		return kategorie;
	}

	public void setKategorie(Kategorie kategorie) {
		this.kategorie = kategorie;
	}

	public CategoryAndPercentHolder(int percentage, Kategorie kategorie) {
		super();
		this.percentage = percentage;
		this.kategorie = kategorie;
	}
	
	public CategoryAndPercentHolder() {
		// TODO Auto-generated constructor stub
	}
}
