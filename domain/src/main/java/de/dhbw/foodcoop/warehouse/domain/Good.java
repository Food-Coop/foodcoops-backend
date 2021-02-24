package de.dhbw.foodcoop.warehouse.domain;
import org.apache.commons.lang3.Validate;

public class Good {
    private final String name;
    private final Kind kind;

    public Good(String name, Kind kind) {
        Validate.notBlank(name);
        this.name = name;
        this.kind = kind;
    }
}
