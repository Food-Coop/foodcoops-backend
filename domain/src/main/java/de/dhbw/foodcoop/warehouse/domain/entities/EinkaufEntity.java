package de.dhbw.foodcoop.warehouse.domain.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.dhbw.foodcoop.warehouse.domain.values.EinkaufBestellungVergleich;

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
	
    @OneToMany(mappedBy = "einkauf", cascade = CascadeType.ALL, orphanRemoval = true)
	List<EinkaufBestellungVergleich> einkauf;
	
	@Column
	private Timestamp date;
	
	
	@Column
	private double breadPriceAtTime;
	
	@Column
	private double freshPriceAtTime;
	
	public EinkaufEntity() {
		// TODO Auto-generated constructor stub
	}

	public EinkaufEntity(String id, String personId, List<EinkaufBestellungVergleich> einkauf, Timestamp date) {
		super();
		this.id = id;
		this.personId = personId;
		this.einkauf = einkauf;
		this.date = date;
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

	public List<EinkaufBestellungVergleich> getEinkauf() {
		return einkauf;
	}

	public void setEinkauf(List<EinkaufBestellungVergleich> einkauf) {
		this.einkauf = einkauf;
	}
	
	
}
