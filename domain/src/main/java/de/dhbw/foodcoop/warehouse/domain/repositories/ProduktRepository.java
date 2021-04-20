package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;

import java.util.List;
import java.util.Optional;

public interface ProduktRepository {
    List<Produkt> alle();

    Produkt speichern(Produkt produkt);

    Optional<Produkt> findeMitId(String id);

    void deleteById(String id);
}
