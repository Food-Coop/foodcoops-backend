package de.dhbw.foodcoop.warehouse.domain.values;

import org.apache.commons.lang3.Validate;

import java.util.Objects;

public final class Produkt {
    private final String name;
    private final Kategorie kategorie;

    public Produkt(String name, Kategorie kategorie) {
        Validate.notBlank(name);
        Validate.notNull(kategorie);
        this.name = name;
        this.kategorie = kategorie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produkt produkt = (Produkt) o;
        return name.equals(produkt.name) && kategorie.equals(produkt.kategorie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, kategorie);
    }
}
