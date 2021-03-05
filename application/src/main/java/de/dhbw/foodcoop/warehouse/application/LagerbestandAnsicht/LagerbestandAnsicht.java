package de.dhbw.foodcoop.warehouse.application.LagerbestandAnsicht;

import de.dhbw.foodcoop.warehouse.domain.entities.Lagerbestand;

import java.util.List;

public interface LagerbestandAnsicht {
    List<Lagerbestand> betrachteAlleLagerbestand();
}
