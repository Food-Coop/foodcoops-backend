package de.dhbw.foodcoop.warehouse.domain.values;

import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.Objects;

public final class Bestellungsliste {
    private final Briefkopf briefkopf;
    private final List<Bestellung> bestellungen;

    public Bestellungsliste(Briefkopf briefkopf, List<Bestellung> bestellungen) {
        Validate.notNull(briefkopf);
        Validate.notNull(bestellungen);
        this.briefkopf = briefkopf;
        this.bestellungen = bestellungen;
    }

    public Briefkopf getBriefkopf() {
        return briefkopf;
    }

    public List<Bestellung> getBestellungen() {
        return bestellungen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bestellungsliste that = (Bestellungsliste) o;
        return briefkopf.equals(that.briefkopf) && bestellungen.equals(that.bestellungen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(briefkopf, bestellungen);
    }
}
