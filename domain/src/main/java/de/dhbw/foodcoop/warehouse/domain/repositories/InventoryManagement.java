package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.values.Good;
import de.dhbw.foodcoop.warehouse.domain.entities.Inventory;

public interface InventoryManagement {
    Inventory changeCurrentInventory(Good good, Integer newCurrentInventory);

    Inventory changeTargetInventory(Good good, Integer newTargetInventory);
}
