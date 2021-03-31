package de.dhbw.foodcoop.warehouse.application.bestellungsliste;

import java.time.LocalDate;

public class Kopf {
    private final String einkaeufer;
    private final LocalDate datum;

    public Kopf(String einkaeufer) {
        this.einkaeufer = einkaeufer;
        this.datum = LocalDate.now();
    }

    public String getEinkaeufer() {
        return einkaeufer;
    }

    public LocalDate getDatum() {
        return datum;
    }
}
