package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;

import java.util.List;

public interface ProduktRepository {
    Produkt produktAnlegen(Produkt produkt);

    Produkt produktAktualisieren(Produkt produkt);

    Produkt produktLoeschen(Produkt produkt);

    List<Produkt> alleProdukteAbrufen();
}
