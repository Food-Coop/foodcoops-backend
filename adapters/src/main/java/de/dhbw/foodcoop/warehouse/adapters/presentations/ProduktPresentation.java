package de.dhbw.foodcoop.warehouse.adapters.presentations;

import de.dhbw.foodcoop.warehouse.domain.values.Lagerbestand;

public final class ProduktPresentation {
    private final String id;
    private final String name;
    private final String kategorie;
    private final Lagerbestand lagerbestand;

    public ProduktPresentation(String id, String name, String kategorie, Lagerbestand lagerbestand) {
        this.id = id;
        this.name = name;
        this.kategorie = kategorie;
        this.lagerbestand = lagerbestand;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKategorie() {
        return kategorie;
    }

    public Lagerbestand getLagerbestand() {
        return lagerbestand;
    }

    @Override
    public String toString() {
        return "Produkt{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", kategorie='" + kategorie + '\'' +
                ", lagerbestand=" + lagerbestand +
                '}';
    }
}
