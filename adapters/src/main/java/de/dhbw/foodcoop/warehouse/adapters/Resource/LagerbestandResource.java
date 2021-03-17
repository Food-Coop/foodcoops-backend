package de.dhbw.foodcoop.warehouse.adapters.Resource;

public final class LagerbestandResource {
    private final Integer istLagerbestand;
    private final Integer sollLagerbestand;
    private final String einheit;

    public LagerbestandResource(Integer istLagerbestand, Integer sollLagerbestand, String einheit) {
        this.istLagerbestand = istLagerbestand;
        this.sollLagerbestand = sollLagerbestand;
        this.einheit = einheit;
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
