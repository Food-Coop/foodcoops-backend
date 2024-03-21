package de.dhbw.foodcoop.warehouse.adapters.representations;

public class BrotBestandRepresentation extends BestandRepresentation{

    private long gewicht;
    
	
	
	public BrotBestandRepresentation(String id, String name, boolean verfuegbarkeit, float preis, long gewicht) {
		super(id, name, verfuegbarkeit, preis);
		this.gewicht = gewicht;
	}



	public long getGewicht() {
		return gewicht;
	}
}

