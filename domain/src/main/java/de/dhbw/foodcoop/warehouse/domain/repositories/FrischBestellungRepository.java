package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface FrischBestellungRepository {
    List<FrischBestellung> alle();

    List<FrischBestellung> findeMitDatumNach(Timestamp datum);

    List<FrischBestellung> findeMitDatumNachUndSum(Timestamp datum);

    FrischBestellung speichern(FrischBestellung frischBestellung);

    Optional<FrischBestellung> findeMitId(String id);

    void deleteById(String id);
}
