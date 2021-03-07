package de.dhbw.foodcoop.warehouse.domain.values;

import org.apache.commons.lang3.Validate;

import java.util.Objects;

public final class Menge {
    private final Einheit einheit;
    private final Integer menge;

    public Menge(Einheit einheit, Integer menge) {
        Validate.notNull(einheit);
        Validate.inclusiveBetween(0, Integer.MAX_VALUE, (Comparable<Integer>) menge);
        this.einheit = einheit;
        this.menge = menge;
    }

    public Einheit getEinheit() {
        return einheit;
    }

    public Integer getMenge() {
        return menge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menge menge1 = (Menge) o;
        return einheit.equals(menge1.einheit) && menge.equals(menge1.menge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(einheit, menge);
    }
}
