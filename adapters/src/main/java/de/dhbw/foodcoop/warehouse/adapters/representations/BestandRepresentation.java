package de.dhbw.foodcoop.warehouse.adapters.representations;

public class BestandRepresentation {
		protected String id;
	    protected String name;
	    private boolean verfuegbarkeit;
	    private float preis;

	    public BestandRepresentation(String id, String name, boolean verfuegbarkeit, float preis) {
	        this.id = id;
	        this.name = name;
	        this.verfuegbarkeit = verfuegbarkeit;
	        this.preis = preis;
	    }

	    public BestandRepresentation() {
			// TODO Auto-generated constructor stub
		}
	    public String getId() {
	        return id;
	    }

	    public String getName() {
	        return name;
	    }

	    public boolean getVerfuegbarkeit() {
	        return verfuegbarkeit;
	    }



	    public float getPreis() {
	        return preis;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }
}
