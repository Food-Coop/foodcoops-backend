package de.dhbw.foodcoop.warehouse.adapters.representations;

import java.util.Set;

import de.dhbw.foodcoop.warehouse.domain.entities.TooMuchBuyEntity;

public class EinkaufCreateRepresentation {
	private String id;
	private String personId;
	Set<BestellungBuyRepresentation> bestellungsEinkauf;
	Set<BestandBuyRepresentation> bestandEinkauf;
	Set<TooMuchBuyRepresentation> tooMuchEinkauf;
	private String email;
	
	
	
	
	public EinkaufCreateRepresentation(String id, String personId, Set<BestellungBuyRepresentation> bestellungsEinkauf,
			Set<BestandBuyRepresentation> bestandEinkauf, Set<TooMuchBuyRepresentation> tooMuchEinkauf, String email) {
		super();
		this.id = id;
		this.tooMuchEinkauf = tooMuchEinkauf;
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
	public Set<TooMuchBuyRepresentation> getTooMuchEinkauf() {
		return tooMuchEinkauf;
	}
	
}
