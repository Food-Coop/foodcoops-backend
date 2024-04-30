package de.dhbw.foodcoop.warehouse.adapters.representations;

public final class ProduktRepresentation extends BestandRepresentation {
    private final KategorieRepresentation kategorie;
    private final LagerbestandRepresentation lagerbestand;
    
    private String produktBezeichnung;

    public ProduktRepresentation(String id, String name, String produktBezeichnung, KategorieRepresentation kategorie, LagerbestandRepresentation lagerbestand, float price) {
    	super(id, name, lagerbestand == null ? false : lagerbestand.getIstLagerbestand() > 0 ? true : false, price);

        this.kategorie = kategorie;
        this.produktBezeichnung = produktBezeichnung;
        this.lagerbestand = lagerbestand;
    }



    public KategorieRepresentation getKategorie() {
        return kategorie;
    }

    public LagerbestandRepresentation getLagerbestand() {
        return lagerbestand;
    }




	public String getProduktBezeichnung() {
		return produktBezeichnung;
	}



	public void setProduktBezeichnung(String produktBezeichnung) {
		this.produktBezeichnung = produktBezeichnung;
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
