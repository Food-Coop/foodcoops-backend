package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestandRepresentation;
import de.dhbw.foodcoop.warehouse.application.lager.EinheitService;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.exceptions.EinheitNotFoundException;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;

@Component
public class RepresentationToFrischBestandMapper implements Function<FrischBestandRepresentation, FrischBestand> {

    private final EinheitService einheitService;

    @Autowired
    public RepresentationToFrischBestandMapper(EinheitService einheitService) {
        this.einheitService = einheitService;
    }

    @Override
    public FrischBestand apply(FrischBestandRepresentation frischBestandRepresentation) {
        Einheit einheit = einheitService.findById(frischBestandRepresentation.getEinheit().getId()).orElseThrow
                (() -> new EinheitNotFoundException(frischBestandRepresentation.getEinheit().getId()));
        return new FrischBestand(
            frischBestandRepresentation.getId(),
            frischBestandRepresentation.getName(),
            frischBestandRepresentation.getVerfuegbarkeit(),
            frischBestandRepresentation.getHerkunftsland(),
            frischBestandRepresentation.getGebindegroesse(),
            einheit,
            frischBestandRepresentation.getPreis());
    }

    public FrischBestand update(FrischBestand oldFrischBestand, FrischBestandRepresentation newFrischBestand) {

        return new FrischBestand(
                oldFrischBestand.getId(),
                pickNewIfDefined(oldFrischBestand.getName(), newFrischBestand.getName()),
                newFrischBestand.getVerfuegbarkeit(),
                pickNewIfDefined(oldFrischBestand.getHerkunftsland(), newFrischBestand.getHerkunftsland()),
                newFrischBestand.getGebindegroesse(),
                newFrischBestand.getEinheit(),
                newFrischBestand.getPreis()
        );
    }

    private String pickNewIfDefined(String oldValue, String newValue) {
        return replaceNullWithUndefined(newValue).equals("undefined") ? oldValue : newValue;
    }

    private String replaceNullWithUndefined(String oldValue) {
        return oldValue == null ? "undefined" : oldValue;
    }
}
