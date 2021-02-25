package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.values.Good;

public interface GoodRepository {
    Good createGood(Good good);

    Good updateGood(Good good);

    Good deleteGood(Good good);
}
