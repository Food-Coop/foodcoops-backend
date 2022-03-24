package de.dhbw.foodcoop.warehouse.adapters.representations;

public final class LagerbestandRepresentation {
    private final EinheitRepresentation einheit;
    private final Double istLagerbestand;
    private final Double sollLagerbestand;

    public LagerbestandRepresentation(EinheitRepresentation einheit, Double istLagerbestand, Double sollLagerbestand) {
        this.einheit = einheit;
        this.istLagerbestand = istLagerbestand;
        this.sollLagerbestand = sollLagerbestand;
    }

    public Double getIstLagerbestand() {
        return istLagerbestand;
    }

    public Double getSollLagerbestand() {
        return sollLagerbestand;
    }

    public EinheitRepresentation getEinheit() {
        return einheit;
    }
}
