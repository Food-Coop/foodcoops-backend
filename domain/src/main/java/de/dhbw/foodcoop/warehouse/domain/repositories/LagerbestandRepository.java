package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.entities.Lagerbestand;
import de.dhbw.foodcoop.warehouse.domain.values.Menge;
import de.dhbw.foodcoop.warehouse.domain.values.Produkt;

import java.util.List;

public interface LagerbestandRepository {
    Lagerbestand istLagerbestandAktualisieren(Produkt produkt, Menge neuerIstLagerbestand);

    Lagerbestand sollLagerbestandAktualisieren(Produkt produkt, Menge neuerSollLagerbestand);

    List<Lagerbestand> abrufenAlleLagerbestand();

    //dummyMethod
    Lagerbestand saveLagerbestand(Lagerbestand lagerbestand);
}
