package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;

import java.util.List;
import java.util.Optional;

public interface DeadlineRepository {
    List<Deadline> alle();

    List<Deadline> letzte();
    
    Deadline speichern(Deadline deadline);

    Optional<Deadline> findeMitId(String id);

    void deleteById(String id);
}
