package de.dhbw.foodcoop.warehouse.domain.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
	List<BestellungBuyEntity> bestellungsEinkauf;
	
    
    //Dies sind weitere Produkte die  z.B. Lagerware, die gekauft wurden
    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    List<BestandBuyEntity> bestandEinkauf;
    
    //Dies sind Produkte aus der zuviel Liste
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<TooMuchBuyEntity> tooMuchEinkauf;
    
	@Column
	private LocalDateTime date;
	
	
	@Column
	private double breadPriceAtTime;
	
	@Column
	private double freshPriceAtTime;
	
	@Column
	private double bestandPriceAtTime;
	
	@Column
	private double tooMuchPriceAtTime;
	
	@Column
	private double deliveryCostAtTime;
	
	public EinkaufEntity() {
		// TODO Auto-generated constructor stub
	}


	public EinkaufEntity(String id, String personId, List<BestellungBuyEntity> einkauf,
			 List<BestandBuyEntity> bestandEinkauf, List<TooMuchBuyEntity> tooMuchEinkauf, LocalDateTime date, double breadPriceAtTime,
			double freshPriceAtTime,  double bestandPriceAtTime, double tooMuchPriceAtTime, double deliveryCostAtTime) {
		super();
		this.id = id;
		this.deliveryCostAtTime = deliveryCostAtTime;
		this.personId = personId;
		this.bestellungsEinkauf = einkauf;
		this.bestandEinkauf = bestandEinkauf;
		this.date = date;
		this.tooMuchEinkauf = tooMuchEinkauf;
		this.breadPriceAtTime = breadPriceAtTime;
		this.freshPriceAtTime = freshPriceAtTime;
		this.bestandPriceAtTime = bestandPriceAtTime;
		this.tooMuchPriceAtTime = tooMuchPriceAtTime;
	}


	public double getBestandPriceAtTime() {
		return bestandPriceAtTime;
	}

	
	public double getDeliveryCostAtTime() {
		return deliveryCostAtTime;
	}


	public void setDeliveryCostAtTime(double deliveryCostAtTime) {
		this.deliveryCostAtTime = deliveryCostAtTime;
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
		return freshPriceAtTime + breadPriceAtTime + bestandPriceAtTime + tooMuchPriceAtTime;
	}

	
	
	public double getTooMuchPriceAtTime() {
		return tooMuchPriceAtTime;
	}


	public void setTooMuchPriceAtTime(double tooMuchPriceAtTime) {
		this.tooMuchPriceAtTime = tooMuchPriceAtTime;
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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
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

	public List<BestellungBuyEntity> getBestellungsEinkauf() {
		return bestellungsEinkauf;
	}

	public void setBestellungsEinkauf(List<BestellungBuyEntity> einkauf) {
		this.bestellungsEinkauf = einkauf;
	}


	public List<TooMuchBuyEntity> getTooMuchEinkauf() {
		return tooMuchEinkauf;
	}


	public void setTooMuchEinkauf(List<TooMuchBuyEntity> tooMuchEinkauf) {
		this.tooMuchEinkauf = tooMuchEinkauf;
	}
	
	
}
