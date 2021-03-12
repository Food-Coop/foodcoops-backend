package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;

import java.util.List;

public interface ProduktRepository {
    List<Produkt> alleProdukteAbrufen();
}
