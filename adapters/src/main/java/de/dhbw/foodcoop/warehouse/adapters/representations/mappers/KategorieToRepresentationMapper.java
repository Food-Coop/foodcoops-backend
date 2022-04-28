package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.KategorieRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.ProduktRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class KategorieToRepresentationMapper implements Function<Kategorie, KategorieRepresentation> {
    private final ProduktToRepresentationMapper mapper;

    @Autowired
    public KategorieToRepresentationMapper(ProduktToRepresentationMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public KategorieRepresentation apply(Kategorie kategorie) {
        return new KategorieRepresentation(kategorie.getId(),
                kategorie.getName()
        );
    }
}
