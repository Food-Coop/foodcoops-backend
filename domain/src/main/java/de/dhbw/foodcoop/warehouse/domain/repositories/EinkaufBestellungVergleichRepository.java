package de.dhbw.foodcoop.warehouse.domain.repositories;

import java.util.List;
import java.util.Optional;

import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import de.dhbw.foodcoop.warehouse.domain.values.EinkaufBestellungVergleich;

public interface EinkaufBestellungVergleichRepository {
    List<EinkaufBestellungVergleich> alle();

    EinkaufBestellungVergleich speichern(EinkaufBestellungVergleich einkaufBestellungVergleich);

    Optional<EinkaufBestellungVergleich> findeMitId(String id);

    void deleteById(String id);
}
