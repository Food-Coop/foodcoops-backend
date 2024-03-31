package de.dhbw.foodcoop.warehouse.adapters.representations;

public class BestellungBuyRepresentation {
	private String id;
	private BestellungRepresentation bestellung;
	private double amount;
	public BestellungBuyRepresentation(String id, BestellungRepresentation bestellung, double amount) {
		super();
		this.id = id;
		this.bestellung = bestellung;
		this.amount = amount;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
	
	public BestellungRepresentation getBestellung() {
		return bestellung;
	}

	public void setBestellung(BestellungRepresentation bestellung) {
		this.bestellung = bestellung;
	}

	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
