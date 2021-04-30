package de.dhbw.foodcoop.warehouse.domain.entities;

import java.util.Objects;

public final class Mitglied {
    private String id;

    public Mitglied(String id) {
        this.id = id;
    }

    protected Mitglied() {
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mitglied mitglied = (Mitglied) o;
        return id.equals(mitglied.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
