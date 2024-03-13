package de.dhbw.foodcoop.warehouse.domain.repositories;

import java.util.List;
import java.util.Optional;

import de.dhbw.foodcoop.warehouse.domain.entities.BestandBuyEntity;

public interface BestandBuyRepository {
    List<BestandBuyEntity> alle();

    BestandBuyEntity speichern(BestandBuyEntity produkt);

    Optional<BestandBuyEntity> findeMitId(String id);

    void deleteById(String id);
}
