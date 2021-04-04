package de.dhbw.foodcoop.warehouse.application.bestellungsliste;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

public class KopfService {
    private final String einkaeufer;
    private final LocalDate datum;

    public KopfService(String einkaeufer) {
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
