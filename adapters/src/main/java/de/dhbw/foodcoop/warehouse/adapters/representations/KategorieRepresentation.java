package de.dhbw.foodcoop.warehouse.adapters.representations;

import java.util.List;

public final class KategorieRepresentation {
    private final String name;
    private final String icon;
    private final List<ProduktRepresentation> produkte;
    private String id;

    public KategorieRepresentation(String id, String name, String icon, List<ProduktRepresentation> produkte) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.produkte = produkte;
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

    public String getIcon() {
        return icon;
    }

    public List<ProduktRepresentation> getProdukte() {
        return produkte;
    }
}
