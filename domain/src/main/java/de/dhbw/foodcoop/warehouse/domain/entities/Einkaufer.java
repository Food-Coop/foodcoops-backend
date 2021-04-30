package de.dhbw.foodcoop.warehouse.domain.entities;

import java.util.Objects;

public final class Einkaufer {
    private String id;


    public Einkaufer(String id) {
        this.id = id;

    }

    protected Einkaufer() {
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Einkaufer einkaufer = (Einkaufer) o;
        return id.equals(einkaufer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
