package de.dhbw.foodcoop.warehouse.adapters.representations;

import java.util.Set;

public class EinkaufCreateRepresentation {
	private String id;
	private String personId;
	Set<BestellungRepresentation> bestellungsEinkauf;
	Set<BestandBuyRepresentation> bestandEinkauf;
	
	
	
	
	public EinkaufCreateRepresentation(String id, String personId, Set<BestellungRepresentation> bestellungsEinkauf,
			Set<BestandBuyRepresentation> bestandEinkauf) {
		super();
		this.id = id;
		this.personId = personId;
		this.bestellungsEinkauf = bestellungsEinkauf;
		this.bestandEinkauf = bestandEinkauf;
	}
	public String getId() {
		return id;
	}
	public String getPersonId() {
		return personId;
	}
	public Set<BestellungRepresentation> getBestellungsEinkauf() {
		return bestellungsEinkauf;
	}
	public Set<BestandBuyRepresentation> getBestandEinkauf() {
		return bestandEinkauf;
	}
	public void setId(String id) {
		this.id = id;
		// TODO Auto-generated method stub
		
	}
	
	
}
