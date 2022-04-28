package de.dhbw.foodcoop.warehouse.adapters.representations;

import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;

public class FrischBestandRepresentation {
    private String id;
    private String name;
    private boolean verfuegbarkeit;
    private String herkunftsland;
    private int gebindegroesse;
    private Einheit einheit;
    private Kategorie kategorie;
    private float preis;

    public FrischBestandRepresentation(String id, String name, boolean verfuegbarkeit, String herkunftsland, int gebindegroesse, Einheit einheit, Kategorie kategorie, float preis) {
        this.id = id;
        this.name = name;
        this.verfuegbarkeit = verfuegbarkeit;
        this.herkunftsland = herkunftsland;
        this.gebindegroesse = gebindegroesse;
        this.einheit = einheit;
        this.kategorie = kategorie;
        this.preis = preis;
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

    public String getHerkunftsland() {
        return herkunftsland;
    }

    public int getGebindegroesse() {
        return gebindegroesse;
    }

    public Einheit getEinheit() {
        return einheit;
    }

    public Kategorie getKategorie() {
        return kategorie;
    }

    public float getPreis() {
        return preis;
    }

    public void setId(String id) {
        this.id = id;
    }
}

