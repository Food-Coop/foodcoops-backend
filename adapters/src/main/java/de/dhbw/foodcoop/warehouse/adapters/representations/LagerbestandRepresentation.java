package de.dhbw.foodcoop.warehouse.adapters.representations;

public final class LagerbestandRepresentation {
    private final EinheitRepresentation einheitRepresentation;
    private final Double istLagerbestand;
    private final Double sollLagerbestand;

    public LagerbestandRepresentation(EinheitRepresentation einheitRepresentation, Double istLagerbestand, Double sollLagerbestand) {
        this.einheitRepresentation = einheitRepresentation;
        this.istLagerbestand = istLagerbestand;
        this.sollLagerbestand = sollLagerbestand;
    }

    public Double getIstLagerbestand() {
        return istLagerbestand;
    }

    public Double getSollLagerbestand() {
        return sollLagerbestand;
    }

    public EinheitRepresentation getEinheitRepresentation() {
        return einheitRepresentation;
    }
}
