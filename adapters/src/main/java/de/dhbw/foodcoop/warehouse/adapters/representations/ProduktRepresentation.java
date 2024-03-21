package de.dhbw.foodcoop.warehouse.adapters.representations;

public final class ProduktRepresentation extends BestandRepresentation {
    private final KategorieRepresentation kategorie;
    private final LagerbestandRepresentation lagerbestand;

    public ProduktRepresentation(String id, String name, KategorieRepresentation kategorie, LagerbestandRepresentation lagerbestand) {
    	this.id = id;
    	this.name = name;
        this.kategorie = kategorie;
        this.lagerbestand = lagerbestand;
    }



    public KategorieRepresentation getKategorie() {
        return kategorie;
    }

    public LagerbestandRepresentation getLagerbestand() {
        return lagerbestand;
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
