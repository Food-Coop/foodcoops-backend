package de.dhbw.foodcoop.warehouse.domain.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author MStaar
 *
 */
@Entity
@Table(name = "einkauf")
public class EinkaufEntity {

	@Id
	private String id;
	
	@Column
	private String personId;
	
	//Dies sind die Frisch/Brot Produkte die tatsächlich von der Bestellung gekauft wurden
    @ManyToMany( cascade = CascadeType.ALL)
	List<BestellungEntity> bestellungsEinkauf;
	
    
    //Dies sind weitere Produkte die  z.B. aus der zuviel Liste oder Lagerware! die gekauft wurden, aber keine Bestellung waren
    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    List<BestandBuyEntity> bestandEinkauf;
    
    
	@Column
	private Timestamp date;
	
	
	@Column
	private double breadPriceAtTime;
	
	@Column
	private double freshPriceAtTime;
	
	@Column
	private double bestandPriceAtTime;
	
	public EinkaufEntity() {
		// TODO Auto-generated constructor stub
	}


	public EinkaufEntity(String id, String personId, List<BestellungEntity> einkauf,
			 List<BestandBuyEntity> bestandEinkauf, Timestamp date, double breadPriceAtTime,
			double freshPriceAtTime,  double bestandPriceAtTime) {
		super();
		this.id = id;
		this.personId = personId;
		this.bestellungsEinkauf = einkauf;
		this.bestandEinkauf = bestandEinkauf;
		this.date = date;
		this.breadPriceAtTime = breadPriceAtTime;
		this.freshPriceAtTime = freshPriceAtTime;
		this.bestandPriceAtTime = bestandPriceAtTime;
	}


	public double getBestandPriceAtTime() {
		return bestandPriceAtTime;
	}


	public void setBestandPriceAtTime(double bestandPriceAtTime) {
		this.bestandPriceAtTime = bestandPriceAtTime;
	}


	public List<BestandBuyEntity> getBestandEinkauf() {
		return bestandEinkauf;
	}

	public void setBestandEinkauf(List<BestandBuyEntity> bestandEinkauf) {
		this.bestandEinkauf = bestandEinkauf;
	}

	public double getTotalPriceAtTime() {
		return freshPriceAtTime + breadPriceAtTime;
	}


	public double getBreadPriceAtTime() {
		return breadPriceAtTime;
	}

	public void setBreadPriceAtTime(double breadPriceAtTime) {
		this.breadPriceAtTime = breadPriceAtTime;
	}

	public double getFreshPriceAtTime() {
		return freshPriceAtTime;
	}

	public void setFreshPriceAtTime(double freshPriceAtTime) {
		this.freshPriceAtTime = freshPriceAtTime;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public List<BestellungEntity> getBestellungsEinkauf() {
		return bestellungsEinkauf;
	}

	public void setBestellungsEinkauf(List<BestellungEntity> einkauf) {
		this.bestellungsEinkauf = einkauf;
	}
	
	
}
