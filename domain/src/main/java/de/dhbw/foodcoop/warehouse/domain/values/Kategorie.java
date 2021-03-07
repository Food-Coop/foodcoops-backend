package de.dhbw.foodcoop.warehouse.domain.values;

import org.apache.commons.lang3.Validate;

import java.util.Objects;

public final class Kategorie {
    private final String name;

    public Kategorie(String name) {
        Validate.notBlank(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kategorie kategorie = (Kategorie) o;
        return name.equals(kategorie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
