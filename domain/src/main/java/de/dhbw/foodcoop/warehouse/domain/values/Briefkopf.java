package de.dhbw.foodcoop.warehouse.domain.values;

import org.apache.commons.lang3.Validate;

import java.time.LocalDate;
import java.util.Objects;

public final class Briefkopf {
    private final String einkaeufer;
    private final LocalDate datum;

    public Briefkopf(String einkaeufer) {
        Validate.notBlank(einkaeufer);
        this.einkaeufer = einkaeufer;
        this.datum = LocalDate.now();
    }

    public String getEinkaeufer() {
        return einkaeufer;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public String getDatumString() {
        return datum.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Briefkopf briefkopf = (Briefkopf) o;
        return einkaeufer.equals(briefkopf.einkaeufer) && datum.equals(briefkopf.datum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(einkaeufer, datum);
    }
}
