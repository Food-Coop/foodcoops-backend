package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.values.Einheit;

import java.util.List;
import java.util.Optional;

public interface EinheitRepository {
    List<Einheit> alle();
    Einheit speichern(Einheit einheit);
    Optional<Einheit> findeMitId(String id);
}
