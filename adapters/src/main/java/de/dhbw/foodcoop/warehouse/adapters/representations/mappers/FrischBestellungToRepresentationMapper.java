package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestandRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestellungRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.PersonRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;

@Component
public class FrischBestellungToRepresentationMapper implements Function<FrischBestellung, FrischBestellungRepresentation> {
    private final FrischBestandToRepresentationMapper frischBestandToRepresentationMapper;
    private final PersonToRepresentationMapper personToRepresentationMapper;

    @Autowired
    public FrischBestellungToRepresentationMapper(FrischBestandToRepresentationMapper frischbestandToRepresentationMapper, PersonToRepresentationMapper personToRepresentationMapper) {
        this.frischBestandToRepresentationMapper = frischbestandToRepresentationMapper;
        this.personToRepresentationMapper = personToRepresentationMapper;
    }

    @Override
    public FrischBestellungRepresentation apply(FrischBestellung frischBestellung) {
        FrischBestandRepresentation frischBestandRepresentation =
                frischBestandToRepresentationMapper.apply(frischBestellung.getFrischbestand());
        PersonRepresentation personRepresentation = 
                personToRepresentationMapper.apply(frischBestellung.getPerson());
        return new FrischBestellungRepresentation(
                frischBestellung.getId(),
                frischBestandRepresentation,
                personRepresentation,
                frischBestellung.getDate()
        );
    }
}
