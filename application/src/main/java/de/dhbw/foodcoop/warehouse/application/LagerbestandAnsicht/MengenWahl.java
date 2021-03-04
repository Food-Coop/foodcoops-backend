package de.dhbw.foodcoop.warehouse.application.LagerbestandAnsicht;

import de.dhbw.foodcoop.warehouse.domain.values.Menge;

import java.util.List;

public interface MengenWahl {
    List<Menge> waehleMengeAus();

    Menge addMenge(Menge menge);

    Menge deleteMenge(Menge menge);
}
