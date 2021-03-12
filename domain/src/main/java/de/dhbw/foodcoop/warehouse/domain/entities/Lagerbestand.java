package de.dhbw.foodcoop.warehouse.domain.entities;

import de.dhbw.foodcoop.warehouse.domain.values.Menge;
import org.apache.commons.lang3.Validate;

import java.util.UUID;

public class Lagerbestand {
    private final String id;
    private final Menge istLagerbestand;
    private final Menge sollLagerbestand;
    private Produkt produkt;

    public Lagerbestand(String id, Produkt produkt, Menge istLagerbestand, Menge sollLagerbestand) {
        Validate.notNull(id);
        Validate.notNull(istLagerbestand);
        Validate.notNull(sollLagerbestand);
        this.id = id;
        this.produkt = produkt;
        this.istLagerbestand = istLagerbestand;
        this.sollLagerbestand = sollLagerbestand;
    }

    public Lagerbestand(Menge istLagerbestand, Menge sollLagerbestand) {
        this(UUID.randomUUID().toString(), null, istLagerbestand, sollLagerbestand);
    }

    public String getId() {
        return id;
    }

    public Produkt getProdukt() {
        return produkt;
    }

    public void setProdukt(Produkt produkt) {
        Validate.notNull(produkt);
        this.produkt = produkt;
    }

    public Menge getIstLagerbestand() {
        return istLagerbestand;
    }

    public Menge getSollLagerbestand() {
        return sollLagerbestand;
    }
}
