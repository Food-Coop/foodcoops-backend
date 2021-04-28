package de.dhbw.foodcoop.warehouse.adapters.representations;

public final class ProduktRepresentation {
    private final String name;
    private final String icon;
    private final String kategorie;
    private final LagerbestandRepresentation lagerbestand;
    private String id;

    public ProduktRepresentation(String id, String name, String icon, String kategorie, LagerbestandRepresentation lagerbestand) {
        this.id = id;
        this.name = name;
        this.icon = icon;
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

    public String getKategorie() {
        return kategorie;
    }

    public LagerbestandRepresentation getLagerbestand() {
        return lagerbestand;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "ProduktRepresentation{" +
                "name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", kategorie='" + kategorie + '\'' +
                ", lagerbestand=" + lagerbestand +
                ", id='" + id + '\'' +
                '}';
    }
}
