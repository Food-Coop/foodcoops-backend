package de.dhbw.foodcoop.warehouse.adapters.representations;

public class FrischBestandRepresentation {
    private String id;
    private String name;
    private boolean verfuegbarkeit;
    private String herkunftsland;
    private int gebindegroesse;
    private EinheitRepresentation einheit;
    private KategorieRepresentation kategorie;
    private float preis;

    public FrischBestandRepresentation(String id, String name, boolean verfuegbarkeit, String herkunftsland, int gebindegroesse, EinheitRepresentation einheit, KategorieRepresentation kategorie, float preis) {
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

    public EinheitRepresentation getEinheit() {
        return einheit;
    }

    public KategorieRepresentation getKategorie() {
        return kategorie;
    }

    public float getPreis() {
        return preis;
    }

    public void setId(String id) {
        this.id = id;
    }
}

