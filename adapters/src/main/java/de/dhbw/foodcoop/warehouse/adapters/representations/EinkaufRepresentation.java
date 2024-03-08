package de.dhbw.foodcoop.warehouse.adapters.representations;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import de.dhbw.foodcoop.warehouse.domain.values.EinkaufBestellungVergleich;

public class EinkaufRepresentation {
	private String id;
	
	private String personId;
	
	List<EinkaufBestellungVergleichRepresentation> einkauf;
	
	@Column
	private Timestamp date;
	
	
	@Column
	private double breadPriceAtTime;
	
	@Column
	private double freshPriceAtTime;
}
