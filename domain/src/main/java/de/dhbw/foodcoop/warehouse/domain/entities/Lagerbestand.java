package de.dhbw.foodcoop.warehouse.domain.entities;

import de.dhbw.foodcoop.warehouse.domain.values.Menge;
import org.apache.commons.lang3.Validate;

import java.util.UUID;

public class Lagerbestand {
    private final String uuid;
    private final Produkt produkt;
    private final Menge istLagerbestand;
    private final Menge sollLagerbestand;

    public Lagerbestand(Produkt produkt, Menge istLagerbestand, Menge sollLagerbestand) {
        Validate.notNull(produkt);
        Validate.notNull(istLagerbestand);
        Validate.notNull(sollLagerbestand);
        this.uuid = UUID.randomUUID().toString();
        this.produkt = produkt;
        this.istLagerbestand = istLagerbestand;
        this.sollLagerbestand = sollLagerbestand;
    }
}
