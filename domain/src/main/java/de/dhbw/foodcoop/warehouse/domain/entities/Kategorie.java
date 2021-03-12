package de.dhbw.foodcoop.warehouse.domain.entities;

import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class Kategorie {
    private final String id;
    private final String name;
    private final String icon;
    private final List<Produkt> produkte;

    public Kategorie(String id, String name, String icon, List<Produkt> produkte) {
        Validate.notNull(id);
        Validate.notBlank(name);
        Validate.notNull(icon);
        Validate.notNull(produkte);
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.produkte = produkte;
    }

    public Kategorie(String name, String icon, List<Produkt> produkte) {
        this(UUID.randomUUID().toString(), name, icon, produkte);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kategorie kategorie = (Kategorie) o;
        return id.equals(kategorie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
