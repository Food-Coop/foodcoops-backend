package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.BrotBestandRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.BrotBestellungRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;

@Component
public class BrotBestellungToRepresentationMapper implements Function<BrotBestellung, BrotBestellungRepresentation> {
    private final BrotBestandToRepresentationMapper brotBestandToRepresentationMapper;

    @Autowired
    public BrotBestellungToRepresentationMapper(BrotBestandToRepresentationMapper brotBestandToRepresentationMapper) {
        this.brotBestandToRepresentationMapper = brotBestandToRepresentationMapper;
    }

    @Override
    public BrotBestellungRepresentation apply(BrotBestellung brotBestellung) {
        BrotBestandRepresentation brotBestandRepresentation =
                brotBestandToRepresentationMapper.apply(brotBestellung.getBrotBestand());
        return new BrotBestellungRepresentation(
                brotBestellung.getId(),
                brotBestellung.getPersonId(),
                brotBestandRepresentation,
                brotBestellung.getBestellmenge(),
                brotBestellung.getDatum()
        );
    }
}
