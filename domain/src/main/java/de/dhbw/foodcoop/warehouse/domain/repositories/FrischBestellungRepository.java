package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface FrischBestellungRepository {
    List<FrischBestellung> alle();
    
    List<FrischBestellung> findeAlleVonPerson(String person_id);

    List<FrischBestellung> findeMitDatumNachUndPerson(Timestamp datum, String person_id);

    List<FrischBestellung> findeMitDatumNachUndSum(Timestamp datum);

    List<FrischBestellung> findeMitDatumZwischen(Timestamp datum1, Timestamp datum2, String person_id);

    FrischBestellung speichern(FrischBestellung frischBestellung);

    Optional<FrischBestellung> findeMitId(String id);
    
    List<FrischBestellung> findeAlleBestellungenNachDatum(Timestamp datum);

    void deleteById(String id);
}
