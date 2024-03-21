package de.dhbw.foodcoop.warehouse.plugins.helpObjects;

import java.util.List;

import de.dhbw.foodcoop.warehouse.domain.entities.BestandBuyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellungEntity;

public class EinkaufRequest {

	private String personId;
	private List<BestellungEntity> bestellungen;
	private List<BestandBuyEntity> buy;
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public List<BestellungEntity> getBestellungen() {
		return bestellungen;
	}
	public void setBestellungen(List<BestellungEntity> bestellungen) {
		this.bestellungen = bestellungen;
	}
	public List<BestandBuyEntity> getBuy() {
		return buy;
	}
	public void setBuy(List<BestandBuyEntity> buy) {
		this.buy = buy;
	}
	
	
	
}
