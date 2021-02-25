package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.values.Good;

public interface GoodManagement {
    Good createGood(Good good);

    Good changeGood(Good good);

    Good deleteGood(Good good);
}
