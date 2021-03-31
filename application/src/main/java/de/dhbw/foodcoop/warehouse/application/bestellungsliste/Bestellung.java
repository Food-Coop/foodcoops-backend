package de.dhbw.foodcoop.warehouse.application.bestellungsliste;

import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;

import java.util.function.Function;

public class Bestellung implements Function<Produkt, String> {
    private final StringBuilder bestellung;

    public Bestellung( ) {
        this.bestellung = new StringBuilder();
    }

    @Override
    public String apply(Produkt produkt) {
        return null;
    }
}
