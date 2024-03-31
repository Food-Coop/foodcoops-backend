package de.dhbw.foodcoop.warehouse.adapters.representations;

public class BrotBestandRepresentation extends BestandRepresentation{

    private double gewicht;
    
	
	
	public BrotBestandRepresentation(String id, String name, boolean verfuegbarkeit, float preis, double gewicht) {
		super(id, name, verfuegbarkeit, preis);
		this.gewicht = gewicht;
	}



	public double getGewicht() {
		return gewicht;
	}
}

