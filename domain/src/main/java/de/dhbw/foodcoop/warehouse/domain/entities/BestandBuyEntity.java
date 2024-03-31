package de.dhbw.foodcoop.warehouse.domain.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class BestandBuyEntity {

	
	@Id
	private String id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bestand_id")
	private Produkt bestand;
	
	@Column
	private double amount;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Produkt getBestand() {
		return bestand;
	}

	public void setBestand(Produkt bestand) {
		this.bestand = bestand;
	}

	public BestandBuyEntity() {
		// TODO Auto-generated constructor stub
	}
	public BestandBuyEntity(String id, Produkt bestand, double amount) {
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


	

	
	
}
