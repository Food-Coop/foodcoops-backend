package de.dhbw.foodcoop.warehouse.domain.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name ="BestellUebersicht")
public class BestellUebersicht {

	@Id
	private String id;
	
	//Frisch Bestellungen
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DiscrepancyEntity> discrepancy;
	
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BrotBestellung> brotBestellung;

    @OneToOne
    @JoinColumn(name = "toOrderWithinDeadline_id")
	private Deadline toOrderWithinDeadline; 
	
	public BestellUebersicht() {
		// TODO Auto-generated constructor stub
	}
	
	
	public BestellUebersicht(List<DiscrepancyEntity> discrepancy, List<BrotBestellung> brotBestellung, Deadline toOrderWithinDeadline, String id) {
		super();
		this.discrepancy = discrepancy;
		this.brotBestellung = brotBestellung;
		this.toOrderWithinDeadline = toOrderWithinDeadline;
		this.id = id;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public List<DiscrepancyEntity> getDiscrepancy() {
		return discrepancy;
	}


	public void setDiscrepancy(List<DiscrepancyEntity> discrepancy) {
		this.discrepancy = discrepancy;
	}


	public List<BrotBestellung> getBrotBestellung() {
		return brotBestellung;
	}


	public void setBrotBestellung(List<BrotBestellung> brotBestellung) {
		this.brotBestellung = brotBestellung;
	}


	public Deadline getToOrderWithinDeadline() {
		return toOrderWithinDeadline;
	}


	public void setToOrderWithinDeadline(Deadline toOrderWithinDeadline) {
		this.toOrderWithinDeadline = toOrderWithinDeadline;
	}
	
	
	
}
