package de.dhbw.foodcoop.warehouse.adapters.Resource;

import java.util.List;

public class KategorieResource {
    private final String id;
    private final String name;
    private final String icon;
    private final List<ProduktResource> produktResources;

    public KategorieResource(String id, String name, String icon, List<ProduktResource> produktResources) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.produktResources = produktResources;
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

    public List<ProduktResource> getProduktResources() {
        return produktResources;
    }
}

