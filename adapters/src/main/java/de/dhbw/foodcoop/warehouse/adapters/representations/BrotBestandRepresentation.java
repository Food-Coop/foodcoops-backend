package de.dhbw.foodcoop.warehouse.adapters.representations;

public class BrotBestandRepresentation {
    private String id;
    private String name;
    private boolean verfuegbarkeit;
    private long gewicht;
    private float preis;

    public BrotBestandRepresentation(String id, String name, boolean verfuegbarkeit, long gewicht, float preis) {
        this.id = id;
        this.name = name;
        this.verfuegbarkeit = verfuegbarkeit;
        this.gewicht = gewicht;
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

    public long getGewicht() {
        return gewicht;
    }

    public float getPreis() {
        return preis;
    }

    public void setId(String id) {
        this.id = id;
    }
}

