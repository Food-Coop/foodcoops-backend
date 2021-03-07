package de.dhbw.foodcoop.warehouse.adapters.Resource;

public class ProduktResource {
    private final String id;
    private final String name;
    private final String kategorie;
    private final LagerbestandResource lagerbestandResource;

    public ProduktResource(String id, String name, String kategorie, LagerbestandResource lagerbestandResource) {
        this.id = id;
        this.name = name;
        this.kategorie = kategorie;
        this.lagerbestandResource = lagerbestandResource;
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

    public LagerbestandResource getLagerbestandResource() {
        return lagerbestandResource;
    }
}
