package de.dhbw.foodcoop.warehouse.adapters.representations;

public final class ProduktRepresentation {
    private final String name;
    private final KategorieRepresentation kategorie;
    private final LagerbestandRepresentation lagerbestand;
    private String id;

    public ProduktRepresentation(String id, String name, KategorieRepresentation kategorie, LagerbestandRepresentation lagerbestand) {
        this.id = id;
        this.name = name;
        this.kategorie = kategorie;
        this.lagerbestand = lagerbestand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
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
