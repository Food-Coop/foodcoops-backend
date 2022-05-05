package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.BrotBestandRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestand;

@Component
public class RepresentationToBrotBestandMapper implements Function<BrotBestandRepresentation, BrotBestand> {

    @Autowired
    public RepresentationToBrotBestandMapper() {
    }

    @Override
    public BrotBestand apply(BrotBestandRepresentation brotBestandRepresentation) {
        return new BrotBestand(
            brotBestandRepresentation.getId(),
            brotBestandRepresentation.getName(),
            brotBestandRepresentation.getVerfuegbarkeit(),
            brotBestandRepresentation.getGewicht(),
            brotBestandRepresentation.getPreis());
    }

    public BrotBestand update(BrotBestand oldBrotBestand, BrotBestandRepresentation newBrotBestand) {
        return new BrotBestand(
            oldBrotBestand.getId(),
                pickNewIfDefined(oldBrotBestand.getName(), newBrotBestand.getName()),
                newBrotBestand.getVerfuegbarkeit(),
                newBrotBestand.getGewicht(),
                newBrotBestand.getPreis()
        );
    }

    private String pickNewIfDefined(String oldValue, String newValue) {
        return replaceNullWithUndefined(newValue).equals("undefined") ? oldValue : newValue;
    }

    private String replaceNullWithUndefined(String oldValue) {
        return oldValue == null ? "undefined" : oldValue;
    }
}
