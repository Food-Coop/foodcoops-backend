package de.dhbw.foodcoop.warehouse.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class BestandBuyEntity {

	
	@Id
	private String id;
	
	@ManyToOne 
	@JoinColumn(name = "bestand_id")
	private BestandEntity bestand;
	
	@Column
	private double amount;
	
    @ManyToOne
    @JoinColumn(name = "einkauf_id")
    private EinkaufEntity einkauf;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BestandEntity getBestand() {
		return bestand;
	}

	public void setBestand(BestandEntity bestand) {
		this.bestand = bestand;
	}

	public BestandBuyEntity() {
		// TODO Auto-generated constructor stub
	}
	public BestandBuyEntity(String id, BestandEntity bestand, double amount) {
		super();
		this.id = id;
		this.bestand = bestand;
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public EinkaufEntity getEinkauf() {
		return einkauf;
	}

	public void setEinkauf(EinkaufEntity einkauf) {
		this.einkauf = einkauf;
	}
	

	
	
}
