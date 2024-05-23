package de.dhbw.foodcoop.warehouse.domain.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import de.dhbw.foodcoop.warehouse.domain.shopping.BuyType;

@Entity
public class BestellungBuyEntity implements BuyType{

	
	@Id
	private String id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bestellung_id")
	private BestellungEntity bestellung;
	
	@Column
	private double amount;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public BestellungBuyEntity(String id, BestellungEntity bestellung, double amount) {
		super();
		this.id = id;
		this.bestellung = bestellung;
		this.amount = amount;
	}

	public BestellungEntity getBestellung() {
		return bestellung;
	}

	public void setBestellung(BestellungEntity bestellung) {
		this.bestellung = bestellung;
	}

	public BestellungBuyEntity() {
		// TODO Auto-generated constructor stub
	}


	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}


	

	
	
}
