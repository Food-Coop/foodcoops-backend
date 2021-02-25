package de.dhbw.foodcoop.warehouse.application;

import de.dhbw.foodcoop.warehouse.domain.values.Amount;
import de.dhbw.foodcoop.warehouse.domain.values.Good;
import org.apache.commons.lang3.Validate;

import java.util.Map;

public class ResupplyList {
    private final Map<Good, Amount> order;

    public ResupplyList(Map<Good, Amount> order) {
        Validate.notEmpty(order);
        this.order = order;
    }
}
