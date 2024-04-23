package de.dhbw.foodcoop.warehouse.domain.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TooMuchBuyEntity {
	@Id
	private String id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "discrepancy_id")
	private DiscrepancyEntity discrepancy;
	
	
	@Column
	private double amount;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public DiscrepancyEntity getDiscrepancy() {
		return discrepancy;
	}


	public void setDiscrepancy(DiscrepancyEntity discrepancy) {
		this.discrepancy = discrepancy;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public TooMuchBuyEntity() {
		// TODO Auto-generated constructor stub
	}


	public TooMuchBuyEntity(String id, DiscrepancyEntity discrepancy, double amount) {
		super();
		this.id = id;
		this.discrepancy = discrepancy;
		this.amount = amount;
	}
	
	
}
