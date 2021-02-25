package de.dhbw.foodcoop.warehouse.domain.entities;

import de.dhbw.foodcoop.warehouse.domain.values.Amount;
import de.dhbw.foodcoop.warehouse.domain.values.Good;
import org.apache.commons.lang3.Validate;

public class Stock {
    private final Good good;
    private final Amount currentStock;
    private final Amount targetStock;

    public Stock(Amount currentStock, Amount targetStock, Good good) {
        Validate.notNull(good);
        this.good = good;
        this.currentStock = currentStock;
        this.targetStock = targetStock;
    }
}
