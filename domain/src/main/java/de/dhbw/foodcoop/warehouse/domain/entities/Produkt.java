package de.dhbw.foodcoop.warehouse.domain.entities;

import de.dhbw.foodcoop.warehouse.domain.values.Kategorie;
import org.apache.commons.lang3.Validate;

import java.util.Objects;
import java.util.UUID;

public class Produkt {
    private final String id;
    private final String name;
    private Kategorie kategorie;
    private Lagerbestand lagerbestand;

    public Produkt(String name, Kategorie kategorie, Lagerbestand lagerbestand) {
        Validate.notBlank(name);
        Validate.notNull(kategorie);
        Validate.notNull(lagerbestand);
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.kategorie = kategorie;
        this.lagerbestand = lagerbestand;
    }
}
