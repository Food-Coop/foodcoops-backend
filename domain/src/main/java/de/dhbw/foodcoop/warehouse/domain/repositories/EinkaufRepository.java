package de.dhbw.foodcoop.warehouse.domain.repositories;

import java.util.List;
import java.util.Optional;

import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.EinkaufEntity;

public interface EinkaufRepository {

    void deleteById(String id);
    
    List<EinkaufEntity> alle();

    List<EinkaufEntity> alleVonPerson(String person_id); 
    
    EinkaufEntity speichern(EinkaufEntity einkaufId);

    Optional<EinkaufEntity> findeMitId(String id);
}
