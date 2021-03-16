package de.dhbw.foodcoop.warehouse.adapters.Resource;

public class LagerbestandResource {
    private final String id;
    private final Integer istLagerbestand;
    private final Integer sollLagerbestand;
    private final String einheit;

    public LagerbestandResource(String id, Integer istLagerbestand, Integer sollLagerbestand, String einheit) {
        this.id = id;
        this.istLagerbestand = istLagerbestand;
        this.sollLagerbestand = sollLagerbestand;
        this.einheit = einheit;
    }

    public String getId() {
        return id;
    }

    public Integer getIstLagerbestand() {
        return istLagerbestand;
    }

    public Integer getSollLagerbestand() {
        return sollLagerbestand;
    }

    public String getEinheit() {
        return einheit;
    }
}
