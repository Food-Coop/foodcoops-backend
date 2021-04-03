package de.dhbw.foodcoop.warehouse.application.bestellungsliste;

import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import org.springframework.stereotype.Component;

import java.util.function.Function;

public class BestellungMapper implements Function<Produkt, Bestellung> {

    @Override
    public Bestellung apply(Produkt produkt) {
        return new Bestellung();
    }
}
