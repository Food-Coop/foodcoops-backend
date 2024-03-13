package de.dhbw.foodcoop.warehouse.domain.values;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.dhbw.foodcoop.warehouse.domain.entities.BestellungEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.EinkaufEntity;

@Entity
@Table
public class EinkaufBestellungVergleich {
	
    @Id
    private String id;
    
    @OneToOne
    @JoinColumn(name = "Bestellung_id")
	private BestellungEntity bestellung;
    
    @ManyToOne
    @JoinColumn(name = "einkauf_id")
    private EinkaufEntity einkauf;

	@Column
	private double reeleMenge;
	
	@Column
	private boolean reeleMengeAngegeben;
	
	public EinkaufBestellungVergleich() {
	
	}

	
	public EinkaufBestellungVergleich(String id, BestellungEntity bestellung,
			double reeleMenge, boolean reeleMengeAngegeben, EinkaufEntity einkaufEntity) {
		super();
		this.reeleMengeAngegeben = reeleMengeAngegeben;
		this.einkauf = einkaufEntity;
		this.id = id;
		this.bestellung = bestellung;
		this.reeleMenge = reeleMenge;
	}

	
	
	public EinkaufEntity getEinkauf() {
		return einkauf;
	}


	public void setEinkauf(EinkaufEntity einkauf) {
		this.einkauf = einkauf;
	}


	public boolean isReeleMengeAngegeben() {
		return reeleMengeAngegeben;
	}


	public void setReeleMengeAngegeben(boolean reeleMengeAngegeben) {
		this.reeleMengeAngegeben = reeleMengeAngegeben;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public BestellungEntity getBestellung() {
		return bestellung;
	}


	public void setBestellung(BestellungEntity bestellung) {
		this.bestellung = bestellung;
	}


	public double getReeleMenge() {
		return reeleMenge;
	}

	public void setReeleMenge(double reeleMenge) {
		this.reeleMenge = reeleMenge;
	}
	
	
}
