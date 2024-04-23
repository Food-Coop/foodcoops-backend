package de.dhbw.foodcoop.warehouse.adapters.representations;

public final class ProduktRepresentation extends BestandRepresentation {
    private final KategorieRepresentation kategorie;
    private final LagerbestandRepresentation lagerbestand;
    
    private String produktname;

    public ProduktRepresentation(String id, String name, String produktname, KategorieRepresentation kategorie, LagerbestandRepresentation lagerbestand, float price) {
    	super(id, name, lagerbestand == null ? false : lagerbestand.getIstLagerbestand() > 0 ? true : false, price);

        this.kategorie = kategorie;
        this.produktname = produktname;
        this.lagerbestand = lagerbestand;
    }



    public KategorieRepresentation getKategorie() {
        return kategorie;
    }

    public LagerbestandRepresentation getLagerbestand() {
        return lagerbestand;
    }

    
    public String getProduktname() {
		return produktname;
	}



	public void setProduktname(String produktname) {
		this.produktname = produktname;
	}



	@Override
    public String toString() {
        return "Produkt{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", kategorie='" + kategorie + '\'' +
                ", lagerbestandRepresentation=" + lagerbestand +
                '}';
    }
}
