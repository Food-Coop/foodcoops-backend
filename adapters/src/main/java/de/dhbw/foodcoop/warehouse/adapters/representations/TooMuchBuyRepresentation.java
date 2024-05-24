package de.dhbw.foodcoop.warehouse.adapters.representations;

public class TooMuchBuyRepresentation {
	private String id;
	private DiscrepancyRepresentation discrepancy;
	private double amount;
	public TooMuchBuyRepresentation(String id, DiscrepancyRepresentation discrepancy, double amount) {
		super();
		this.id = id;
		this.discrepancy = discrepancy;
		this.amount = amount;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public DiscrepancyRepresentation getDiscrepancy() {
		return discrepancy;
	}
	public void setDiscrepancy(DiscrepancyRepresentation discrepancy) {
		this.discrepancy = discrepancy;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
