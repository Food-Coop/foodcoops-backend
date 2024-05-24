package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;

import java.util.List;
import java.util.Optional;

public interface BrotBestandRepository {
    List<BrotBestand> alle();

    BrotBestand speichern(BrotBestand brot);

    Optional<BrotBestand> findeMitId(String id);

    void deleteById(String id);
    
    List<BrotBestand> alleSortiert();

}
