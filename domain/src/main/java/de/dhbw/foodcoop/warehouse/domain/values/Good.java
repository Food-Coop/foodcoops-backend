package de.dhbw.foodcoop.warehouse.domain.values;

import org.apache.commons.lang3.Validate;

import java.util.Objects;

public final class Good {
    private final String name;
    private final Kind kind;

    public Good(String name, Kind kind) {
        Validate.notBlank(name);
        Validate.notNull(kind);
        this.name = name;
        this.kind = kind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Good good = (Good) o;
        return name.equals(good.name) && kind.equals(good.kind);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, kind);
    }
}
