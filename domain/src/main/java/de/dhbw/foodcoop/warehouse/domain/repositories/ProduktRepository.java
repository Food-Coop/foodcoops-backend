package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;

import java.util.List;
import java.util.Optional;

public interface ProduktRepository {
    List<Produkt> alle();

    Produkt speichern(Produkt produkt);

    Optional<Produkt> findeMitId(String id);
}
