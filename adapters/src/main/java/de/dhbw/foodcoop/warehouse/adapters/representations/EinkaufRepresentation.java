package de.dhbw.foodcoop.warehouse.adapters.representations;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;

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
