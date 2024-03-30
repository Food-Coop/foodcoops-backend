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
	
	// Wenn ein Gebinde z.B. 2,5kg ist, und zuBestellendeGebinde 3, heißt das 2,5 * 3 für gesamte Kg Menge
	@Column
	private int zuBestellendeGebinde;
	
	// Positive Zahl: Menge die zu viel geliefert wurde. In Stückzahl oder Kg
	// Negative Zahl: Menge die zu wenig geliefert wurde. In Stückzahl oder Kg
	@Column
	private float zuVielzuWenig;
	
	// Wie viel bestellt werden würde wenn es keine Gebinde gäb. z.B. 3 Personen bestellen 3Kg = 9Kg (auch wenn gebinde z.B. 8kg wäre)
	@Column
	private float gewollteMenge;
	

	
	public DiscrepancyEntity(String id, BestandEntity bestand, int zuBestellendeGebinde, float zuVielzuWenig, float gewollteMenge) {
		super();
		this.gewollteMenge = gewollteMenge;
		this.id = id;
		this.bestand = bestand;
		this.zuBestellendeGebinde = zuBestellendeGebinde;
		this.zuVielzuWenig = zuVielzuWenig;
	}

	public DiscrepancyEntity() {
	}
	


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}



	public int getZuBestellendeGebinde() {
		return zuBestellendeGebinde;
	}

	public void setZuBestellendeGebinde(int zuBestellendeGebinde) {
		this.zuBestellendeGebinde = zuBestellendeGebinde;
	}

	public float getZuVielzuWenig() {
		return zuVielzuWenig;
	}

	public void setZuVielzuWenig(float zuVielzuWenig) {
		this.zuVielzuWenig = zuVielzuWenig;
	}

	public BestandEntity getBestand() {
		return bestand;
	}




	public void setBestand(BestandEntity bestand) {
		this.bestand = bestand;
	}

	public float getGewollteMenge() {
		return gewollteMenge;
	}

	public void setGewollteMenge(float gewollteMenge) {
		this.gewollteMenge = gewollteMenge;
	}






}
