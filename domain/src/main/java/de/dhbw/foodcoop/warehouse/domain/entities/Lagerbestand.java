package de.dhbw.foodcoop.warehouse.domain.entities;

import de.dhbw.foodcoop.warehouse.domain.values.Menge;
import org.apache.commons.lang3.Validate;

import java.util.UUID;

public class Lagerbestand {
    private final String id;
    private final Produkt produkt;
    private final Menge istLagerbestand;
    private final Menge sollLagerbestand;

    public Lagerbestand(Produkt produkt, Menge istLagerbestand, Menge sollLagerbestand) {
        Validate.notNull(produkt);
        Validate.notNull(istLagerbestand);
        Validate.notNull(sollLagerbestand);
        this.id = UUID.randomUUID().toString();
        this.produkt = produkt;
        this.istLagerbestand = istLagerbestand;
        this.sollLagerbestand = sollLagerbestand;
    }

    public String getId() {
        return id;
    }

    public Produkt getProdukt() {
        return produkt;
    }

    public Menge getIstLagerbestand() {
        return istLagerbestand;
    }

    public Menge getSollLagerbestand() {
        return sollLagerbestand;
    }
}
