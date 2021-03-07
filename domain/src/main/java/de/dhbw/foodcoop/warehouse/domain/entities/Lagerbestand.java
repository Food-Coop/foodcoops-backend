package de.dhbw.foodcoop.warehouse.domain.entities;

import de.dhbw.foodcoop.warehouse.domain.values.Menge;
import org.apache.commons.lang3.Validate;

import java.util.UUID;

public class Lagerbestand {
    private final String id;
    private final Menge istLagerbestand;
    private final Menge sollLagerbestand;

    public Lagerbestand(String id, Menge istLagerbestand, Menge sollLagerbestand) {
        Validate.notNull(id);
        Validate.notNull(istLagerbestand);
        Validate.notNull(sollLagerbestand);
        this.id = id;
        this.istLagerbestand = istLagerbestand;
        this.sollLagerbestand = sollLagerbestand;
    }

    public Lagerbestand(Menge istLagerbestand, Menge sollLagerbestand) {
        this(UUID.randomUUID().toString(), istLagerbestand, sollLagerbestand);
    }

    public String getId() {
        return id;
    }

    public Menge getIstLagerbestand() {
        return istLagerbestand;
    }

    public Menge getSollLagerbestand() {
        return sollLagerbestand;
    }
}
