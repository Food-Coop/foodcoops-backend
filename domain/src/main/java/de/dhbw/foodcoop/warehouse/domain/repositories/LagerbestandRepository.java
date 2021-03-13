package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.entities.Lagerbestand;

public interface LagerbestandRepository {
    Lagerbestand speichern(Lagerbestand lagerbestand);
}
