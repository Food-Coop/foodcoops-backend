package de.dhbw.foodcoop.warehouse.adapters.representations;

public final class EinheitRepresentation {
    private final String id;
    private final String name;

    public EinheitRepresentation(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
