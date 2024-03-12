package de.dhbw.foodcoop.warehouse.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DiscrepancyEntity {

	@Id
	private String id;
	
    @ManyToOne
    @JoinColumn(name = "frischbestand_id")
	private BestandEntity bestand;
	
	@Column
	private float amount;
	
	
	public DiscrepancyEntity(String id, BestandEntity bestand, float amount) {
		super();
		this.id = id;
		this.bestand = bestand;
		this.amount = amount;
	}

	public DiscrepancyEntity() {
	}
	


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




	public float getAmount() {
		return amount;
	}


	public void setAmount(float amount) {
		this.amount = amount;
	}

}
