package de.dhbw.foodcoop.warehouse.adapters.representations;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import de.dhbw.foodcoop.warehouse.domain.entities.BestellungEntity;

public class EinkaufBestellungVergleichRepresentation {
    private String id;
	private BestellungEntity bestellung;

	private double reeleMenge;
	private boolean reeleMengeAngegeben;
	
}
