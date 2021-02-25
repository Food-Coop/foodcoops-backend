package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.values.Kind;

public interface KindRepository {
    Kind createKind(Kind kind);

    Kind updateKind(Kind kind);

    Kind deleteKind(Kind kind);
}
