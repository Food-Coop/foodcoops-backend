package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.values.Produkt;

public interface ProduktRepository {
    Produkt produktAnlegen(Produkt produkt);

    Produkt produktAktualisieren(Produkt produkt);

    Produkt produktLoeschen(Produkt produkt);
}
