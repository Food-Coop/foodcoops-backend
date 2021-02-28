package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.entities.Lagerbestand;
import de.dhbw.foodcoop.warehouse.domain.values.Menge;
import de.dhbw.foodcoop.warehouse.domain.values.Produkt;

public interface LagerbestandRepository {
    Lagerbestand istLagerbestandAktualisieren(Produkt produkt, Menge neuerIstLagerbestand);

    Lagerbestand sollLagerbestandAktualisieren(Produkt produkt, Menge neuerSollLagerbestand);
}
