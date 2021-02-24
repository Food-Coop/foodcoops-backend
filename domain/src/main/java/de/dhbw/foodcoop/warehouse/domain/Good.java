package de.dhbw.foodcoop.warehouse.domain;

public class Good {
    private final String name;
    private final Kind kind;

    public Good(String name, Kind kind) {
        this.name = name;
        this.kind = kind;
    }
}
