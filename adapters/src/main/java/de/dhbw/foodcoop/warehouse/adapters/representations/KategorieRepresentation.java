package de.dhbw.foodcoop.warehouse.adapters.representations;

public final class KategorieRepresentation {
    private final String name;
    private String id;

    public KategorieRepresentation(String id, String name) {
        this.id = id;
        this.name = name;
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
}
