package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestellungRepresentation;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestandService;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.exceptions.FrischBestandNotFoundException;

@Component
public class RepresentationToFrischBestellungMapper implements Function<FrischBestellungRepresentation, FrischBestellung> {
    private final FrischBestandService frischBestandService;

    @Autowired
    public RepresentationToFrischBestellungMapper(FrischBestandService frischBestandService) {
        this.frischBestandService = frischBestandService;
    }

    @Override
    public FrischBestellung apply(FrischBestellungRepresentation frischBestellungRepresentation) {
        FrischBestand frischBestand = frischBestandService.findById(frischBestellungRepresentation.getFrischbestand().getId()).orElseThrow
                (() -> new FrischBestandNotFoundException(frischBestellungRepresentation.getFrischbestand().getId()));
        return new FrischBestellung(
                frischBestellungRepresentation.getId(),
                frischBestellungRepresentation.getPersonId(),
                frischBestand,
                frischBestellungRepresentation.getBestellmenge(),
                frischBestellungRepresentation.getDatum()
        );
    }

    public FrischBestellung update(FrischBestellung oldFrischBestellung, FrischBestellungRepresentation newFrischBestellung) {
        FrischBestand frischBestand = frischBestandService.findById(newFrischBestellung.getFrischbestand().getId()).orElseThrow
                (() -> new FrischBestandNotFoundException(newFrischBestellung.getFrischbestand().getId()));
        return new FrischBestellung(
                oldFrischBestellung.getId(),
                newFrischBestellung.getPersonId(),
                frischBestand,
                newFrischBestellung.getBestellmenge(),
                newFrischBestellung.getDatum()
        );
    }
}

