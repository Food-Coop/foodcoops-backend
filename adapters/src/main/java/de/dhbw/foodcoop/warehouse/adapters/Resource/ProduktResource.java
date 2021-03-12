package de.dhbw.foodcoop.warehouse.adapters.Resource;

public class ProduktResource {
    private final String id;
    private final String name;
    private final LagerbestandResource lagerbestandResource;

    public ProduktResource(String id, String name, LagerbestandResource lagerbestandResource) {
        this.id = id;
        this.name = name;
        this.lagerbestandResource = lagerbestandResource;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LagerbestandResource getLagerbestandResource() {
        return lagerbestandResource;
    }
}
