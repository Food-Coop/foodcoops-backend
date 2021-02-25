package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.values.Kind;

public interface KindManagement {
    Kind createKind(Kind kind);

    Kind changeKind(Kind kind);

    Kind deleteKind(Kind kind);
}
