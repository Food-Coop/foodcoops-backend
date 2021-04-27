package de.dhbw.foodcoop.warehouse.adapters.representations;

public final class ProduktRepresentation {
    private final String name;
    private final String kategorie;
    private final LagerbestandRepresentation lagerbestandRepresentation;
    private String id;

    public ProduktRepresentation(String id, String name, String kategorie, LagerbestandRepresentation lagerbestandRepresentation) {
        this.id = id;
        this.name = name;
        this.kategorie = kategorie;
        this.lagerbestandRepresentation = lagerbestandRepresentation;
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

    public String getKategorie() {
        return kategorie;
    }

    public LagerbestandRepresentation getLagerbestandRepresentation() {
        return lagerbestandRepresentation;
    }

    @Override
    public String toString() {
        return "Produkt{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", kategorie='" + kategorie + '\'' +
                ", lagerbestandRepresentation=" + lagerbestandRepresentation +
                '}';
    }
}
