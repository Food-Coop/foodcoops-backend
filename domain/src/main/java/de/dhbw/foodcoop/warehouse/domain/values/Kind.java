package de.dhbw.foodcoop.warehouse.domain.values;

import org.apache.commons.lang3.Validate;

import java.util.Objects;

public final class Kind {
    private final String name;

    public Kind(String name) {
        Validate.notBlank(name);
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kind kind = (Kind) o;
        return name.equals(kind.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
