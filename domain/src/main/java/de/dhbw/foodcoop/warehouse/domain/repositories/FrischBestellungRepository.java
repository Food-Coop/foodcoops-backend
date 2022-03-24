package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;

import java.util.List;
import java.util.Optional;

public interface FrischBestellungRepository {
    List<FrischBestellung> alle();

    FrischBestellung speichern(FrischBestellung frischBestellung);

    Optional<FrischBestellung> findeMitId(String id);

    void deleteById(String id);
}
