package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.BrotBestellungRepresentation;
import de.dhbw.foodcoop.warehouse.application.brot.BrotBestandService;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.exceptions.BrotBestandNotFoundException;

@Component
public class RepresentationToBrotBestellungMapper implements Function<BrotBestellungRepresentation, BrotBestellung> {
    private final BrotBestandService brotBestandService;

    @Autowired
    public RepresentationToBrotBestellungMapper(BrotBestandService brotBestandService) {
        this.brotBestandService = brotBestandService;
    }

    @Override
    public BrotBestellung apply(BrotBestellungRepresentation brotBestellungRepresentation) {
        BrotBestand brotBestand = brotBestandService.findById(brotBestellungRepresentation.getBrotbestand().getId()).orElseThrow
                (() -> new BrotBestandNotFoundException(brotBestellungRepresentation.getBrotbestand().getId()));
        return new BrotBestellung(
                brotBestellungRepresentation.getId(),
                brotBestellungRepresentation.getPersonId(),
                brotBestand,
                brotBestellungRepresentation.getBestellmenge(),
                brotBestellungRepresentation.getDatum()
        );
    }

    public BrotBestellung update(BrotBestellung oldBrotBestellung, BrotBestellungRepresentation newBrotBestellung) {
        BrotBestand brotBestand = brotBestandService.findById(newBrotBestellung.getBrotbestand().getId()).orElseThrow
                (() -> new BrotBestandNotFoundException(newBrotBestellung.getBrotbestand().getId()));
        return new BrotBestellung(
                oldBrotBestellung.getId(),
                newBrotBestellung.getPersonId(),
                brotBestand,
                newBrotBestellung.getBestellmenge(),
                newBrotBestellung.getDatum()
        );
    }
}

