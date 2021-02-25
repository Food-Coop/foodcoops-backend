package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.values.Good;
import de.dhbw.foodcoop.warehouse.domain.entities.Stock;

public interface StockRepository {
    Stock updateCurrentStock(Good good, Integer newCurrentStock);

    Stock updateTargetStock(Good good, Integer newTargetStock);
}
