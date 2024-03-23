package de.dhbw.foodcoop.warehouse.adapters.representations;

public final class ProduktRepresentation extends BestandRepresentation {
    private final KategorieRepresentation kategorie;
    private final LagerbestandRepresentation lagerbestand;

    public ProduktRepresentation(String id, String name, KategorieRepresentation kategorie, LagerbestandRepresentation lagerbestand, float price) {
    	super(id, name, lagerbestand == null ? false : lagerbestand.getIstLagerbestand() > 0 ? true : false, price);

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
