package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.KategorieRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class KategorieToRepresentationMapper implements Function<Kategorie, KategorieRepresentation> {

    @Lazy
    @Autowired
    public KategorieToRepresentationMapper() {
    }

    @Override
    public KategorieRepresentation apply(Kategorie kategorie) {
        return new KategorieRepresentation(kategorie.getId(),
                kategorie.getName(),
                kategorie.isMixable()
        );
    }
}
