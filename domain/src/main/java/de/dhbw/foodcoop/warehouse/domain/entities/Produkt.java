package de.dhbw.foodcoop.warehouse.domain.entities;

import de.dhbw.foodcoop.warehouse.domain.values.Kategorie;
import org.apache.commons.lang3.Validate;

import java.util.Objects;
import java.util.UUID;

public class Produkt {
    private final String id;
    private final String name;
    private final Kategorie kategorie;
    private final Lagerbestand lagerbestand;

    public Produkt(String name, Kategorie kategorie, Lagerbestand lagerbestand) {
        Validate.notBlank(name);
        Validate.notNull(kategorie);
        Validate.notNull(lagerbestand);
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.kategorie = kategorie;
        this.lagerbestand = lagerbestand;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Kategorie getKategorie() {
        return kategorie;
    }

    public Lagerbestand getLagerbestand() {
        return lagerbestand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produkt produkt = (Produkt) o;
        return id.equals(produkt.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
