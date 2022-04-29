package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface BrotBestellungRepository {
    List<BrotBestellung> alle();

    List<BrotBestellung> findeMitDatumNachUndPerson(Timestamp datum, String person_id);

    List<BrotBestellung> findeMitDatumNachUndSum(Timestamp datum);

    List<BrotBestellung> findeMitDatumZwischen(Timestamp datum1, Timestamp datum2, String person_id);

    BrotBestellung speichern(BrotBestellung frischBestellung);

    Optional<BrotBestellung> findeMitId(String id);

    void deleteById(String id);
}
