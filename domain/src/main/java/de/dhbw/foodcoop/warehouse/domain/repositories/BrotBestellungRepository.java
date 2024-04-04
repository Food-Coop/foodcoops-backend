package de.dhbw.foodcoop.warehouse.domain.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;

public interface BrotBestellungRepository {
    List<BrotBestellung> alle();

    List<BrotBestellung> alleVonPerson(String person_id); 
    
    List<BrotBestellung> findeMitDatumNachUndPerson(LocalDateTime datum, String person_id);

    List<BrotBestellung> findeMitDatumNachUndSum(LocalDateTime datum);
   
    List<BrotBestellung> findAllOrdersAfterDate(LocalDateTime datum);

    List<BrotBestellung> findeMitDatumZwischen(LocalDateTime datum1, LocalDateTime datum2, String person_id);

    BrotBestellung speichern(BrotBestellung brotBestellung);

    Optional<BrotBestellung> findeMitId(String id);

    void deleteById(String id);
}
