package de.dhbw.foodcoop.warehouse.domain.repositories;

import java.util.List;
import java.util.Optional;

import de.dhbw.foodcoop.warehouse.domain.entities.TooMuchBuyEntity;

public interface TooMuchBuyRepository {
    List<TooMuchBuyEntity> alle();

    TooMuchBuyEntity speichern(TooMuchBuyEntity produkt);

    Optional<TooMuchBuyEntity> findeMitId(String id);

    void deleteById(String id);
}
