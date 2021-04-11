package de.dhbw.foodcoop.warehouse.domain.values;

import org.apache.commons.lang3.Validate;

import java.util.Objects;

public final class Bestellung {
    private final String produkt;
    private final String einheit;
    private final Double menge;

    public Bestellung(String produkt, String einheit, Double menge) {
        Validate.notBlank(produkt);
        Validate.notBlank(einheit);
        Validate.notNull(menge);
        Validate.isTrue(0 < menge);
        this.produkt = produkt;
        this.einheit = einheit;
        this.menge = menge;
    }

    public String getProdukt() {
        return produkt;
    }

    public String getEinheit() {
        return einheit;
    }

    public Double getMenge() {
        return menge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bestellung that = (Bestellung) o;
        return produkt.equals(that.produkt) && einheit.equals(that.einheit) && menge.equals(that.menge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produkt, einheit, menge);
    }
}
