package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestellungRepresentation;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestandService;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.Person;
import de.dhbw.foodcoop.warehouse.domain.exceptions.FrischBestandNotFoundException;

@Component
public class RepresentationToFrischBestellungMapper implements Function<FrischBestellungRepresentation, FrischBestellung> {
    private final FrischBestandService frischBestandService;
    private final RepresentationToPersonMapper toPersonMapper;

    @Autowired
    public RepresentationToFrischBestellungMapper(FrischBestandService frischBestandService, RepresentationToPersonMapper toPersonMapper) {
        this.frischBestandService = frischBestandService;
        this.toPersonMapper = toPersonMapper;
    }

    @Override
    public FrischBestellung apply(FrischBestellungRepresentation frischBestellungRepresentation) {
        // FrischBestand frischBestand = frischBestandService.findById(frischBestellungRepresentation.getFrischbestand().getId()).orElseThrow
        //         (() -> new FrischBestandNotFoundException(frischBestellungRepresentation.getFrischbestand().getId()));
        //Person person = toPersonMapper.apply(frischBestellungRepresentation.getPerson());
        return new FrischBestellung(
                frischBestellungRepresentation.getId(),
                frischBestellungRepresentation.getPersonId(),
                frischBestellungRepresentation.getFrischbestandId(),
                frischBestellungRepresentation.getBestellmenge(),
                frischBestellungRepresentation.getDatum()
        );
    }

    public FrischBestellung update(FrischBestellung oldFrischBestellung, FrischBestellungRepresentation newFrischBestellung) {
        return new FrischBestellung(
                oldFrischBestellung.getId(),
                newFrischBestellung.getPersonId(),
                newFrischBestellung.getFrischbestandId(),
                newFrischBestellung.getBestellmenge(),
                newFrischBestellung.getDatum()
        );
    }
}

