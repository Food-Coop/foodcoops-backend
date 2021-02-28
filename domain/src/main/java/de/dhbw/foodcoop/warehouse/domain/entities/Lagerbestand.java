package de.dhbw.foodcoop.warehouse.domain.entities;

import de.dhbw.foodcoop.warehouse.domain.values.Menge;
import de.dhbw.foodcoop.warehouse.domain.values.Produkt;
import org.apache.commons.lang3.Validate;

public class Lagerbestand {
    private final Produkt produkt;
    private final Menge istLagerbestand;
    private final Menge sollLagerbestand;

    public Lagerbestand(Menge istLagerbestand, Menge sollLagerbestand, Produkt produkt) {
        Validate.notNull(produkt);
        this.produkt = produkt;
        this.istLagerbestand = istLagerbestand;
        this.sollLagerbestand = sollLagerbestand;
    }
}
