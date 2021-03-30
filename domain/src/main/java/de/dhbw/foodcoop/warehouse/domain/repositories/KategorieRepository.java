package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;

import java.util.List;
import java.util.Optional;

public interface KategorieRepository {
    List<Kategorie> alle();

    Kategorie speichern(Kategorie initKategorieGemuese);

    Optional<Kategorie> findeMitId(String id);
}
