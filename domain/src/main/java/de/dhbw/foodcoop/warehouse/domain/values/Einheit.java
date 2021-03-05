package de.dhbw.foodcoop.warehouse.domain.values;

import org.apache.commons.lang3.Validate;

import java.util.Objects;

public final class Einheit {
    private final String name;

    public Einheit(String name) {
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
        Einheit einheit = (Einheit) o;
        return name.equals(einheit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
