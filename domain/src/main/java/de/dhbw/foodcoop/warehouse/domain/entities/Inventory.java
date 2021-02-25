package de.dhbw.foodcoop.warehouse.domain.entities;

import de.dhbw.foodcoop.warehouse.domain.values.Amount;
import de.dhbw.foodcoop.warehouse.domain.values.Good;
import org.apache.commons.lang3.Validate;

public class Inventory {
    private final Good good;
    private final Amount currentInventory;
    private final Amount targetInventory;

    public Inventory(Amount currentInventory, Amount targetInventory, Good good) {
        Validate.notNull(good);
        this.good = good;
        this.currentInventory = currentInventory;
        this.targetInventory = targetInventory;
    }
}
