package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;

import java.util.List;
import java.util.Optional;

public interface FrischBestandRepository {
    List<FrischBestand> alle();

    FrischBestand speichern(FrischBestand frischbestand);

    Optional<FrischBestand> findeMitId(String id);

    void deleteById(String id);

}
