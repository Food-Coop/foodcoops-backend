package de.dhbw.foodcoop.warehouse.application.bestellungsliste;

import java.util.List;

public class ExterneBestellungsliste {
    private final Kopf kopf;
    private final List<Bestellung> bestellungen;

    public ExterneBestellungsliste(Kopf kopf, List<Bestellung> bestellungen) {
        this.kopf = kopf;
        this.bestellungen = bestellungen;
    }

    public Kopf getKopf() {
        return kopf;
    }

    public List<Bestellung> getBestellungen() {
        return bestellungen;
    }
}
