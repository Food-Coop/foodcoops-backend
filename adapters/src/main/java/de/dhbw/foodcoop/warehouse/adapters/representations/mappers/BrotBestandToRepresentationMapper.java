package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import de.dhbw.foodcoop.warehouse.adapters.representations.BrotBestandRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestand;

@Component
public class BrotBestandToRepresentationMapper implements Function<BrotBestand, BrotBestandRepresentation> {

    @Lazy
    @Autowired
    public BrotBestandToRepresentationMapper() {

    }

    @Override
    public BrotBestandRepresentation apply(BrotBestand brotBestand) {
       return new BrotBestandRepresentation(
                brotBestand.getId(),
                brotBestand.getName(),
                brotBestand.getVerfuegbarkeit(),
                brotBestand.getGewicht(),
                brotBestand.getPreis());
    }
}
