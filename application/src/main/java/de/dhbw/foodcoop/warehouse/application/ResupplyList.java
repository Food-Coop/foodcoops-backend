package de.dhbw.foodcoop.warehouse.application;

import de.dhbw.foodcoop.warehouse.domain.values.Menge;
import de.dhbw.foodcoop.warehouse.domain.values.Produkt;
import org.apache.commons.lang3.Validate;

import java.util.Map;

public class ResupplyList {
    private final Map<Produkt, Menge> order;

    public ResupplyList(Map<Produkt, Menge> order) {
        Validate.notEmpty(order);
        this.order = order;
    }
}
