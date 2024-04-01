package de.dhbw.foodcoop.warehouse.adapters.representations;

import java.util.Set;

public class EinkaufCreateRepresentation {
	private String id;
	private String personId;
	Set<BestellungBuyRepresentation> bestellungsEinkauf;
	Set<BestandBuyRepresentation> bestandEinkauf;
	private String email;
	
	
	
	
	public EinkaufCreateRepresentation(String id, String personId, Set<BestellungBuyRepresentation> bestellungsEinkauf,
			Set<BestandBuyRepresentation> bestandEinkauf, String email) {
		super();
		this.id = id;
		this.personId = personId;
		this.bestellungsEinkauf = bestellungsEinkauf;
		this.bestandEinkauf = bestandEinkauf;
		this.email = email;
	}
	public String getId() {
		return id;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPersonId() {
		return personId;
	}
	public Set<BestellungBuyRepresentation> getBestellungsEinkauf() {
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
