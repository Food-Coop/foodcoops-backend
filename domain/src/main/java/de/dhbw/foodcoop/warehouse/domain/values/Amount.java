package de.dhbw.foodcoop.warehouse.domain.values;

import org.apache.commons.lang3.Validate;

import java.util.Objects;

public final class Amount {
    private final String unit;
    private final Integer amount;

    public Amount(String unit, Integer amount) {
        Validate.notBlank(unit);
        Validate.inclusiveBetween(0, Integer.MAX_VALUE, (Comparable<Integer>) amount);
        this.unit = unit;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount amount1 = (Amount) o;
        return unit.equals(amount1.unit) && amount.equals(amount1.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit, amount);
    }
}
