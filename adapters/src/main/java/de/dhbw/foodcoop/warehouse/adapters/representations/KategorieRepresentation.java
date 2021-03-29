package de.dhbw.foodcoop.warehouse.adapters.representations;

import java.util.List;

public class KategorieRepresentation {
    private final String id;
    private final String name;
    private final String icon;
    private final List<ProduktRepresentation> produkte;

    public KategorieRepresentation(String id, String name, String icon, List<ProduktRepresentation> produkte) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.produkte = produkte;
    }

    public String getId() {
        return id;
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
