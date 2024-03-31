package de.dhbw.foodcoop.warehouse.domain.repositories;

import java.util.List;
import java.util.Optional;

import de.dhbw.foodcoop.warehouse.domain.entities.BestellUebersicht;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;

public interface BestellÜbersichtRepository {
	BestellUebersicht findeMitDeadline(Deadline deadline);

    BestellUebersicht speichern(BestellUebersicht bestellÜbersicht);

    Optional<BestellUebersicht> findeMitId(String id);

    void deleteById(String id);
}
