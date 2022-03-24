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
        FrischBestand frischBestand = frischBestandService.findById(frischBestellungRepresentation.getFrischbestand().getId()).orElseThrow
                (() -> new FrischBestandNotFoundException(frischBestellungRepresentation.getFrischbestand().getId()));
        Person person = toPersonMapper.apply(frischBestellungRepresentation.getPerson());
        return new FrischBestellung(
                frischBestellungRepresentation.getId(),
                frischBestand,
                person,
                frischBestellungRepresentation.getDate()
        );
    }

    public FrischBestellung update(FrischBestellung oldFrischBestellung, FrischBestellungRepresentation newFrischBestellung) {
        Optional<FrischBestand> newFrischBestand = newFrischBestellung.getFrischbestand() == null ||
                newFrischBestellung.getFrischbestand().equals("undefined") ?
                Optional.empty() :
                frischBestandService.findById(newFrischBestellung.getFrischbestand().getId());
       
        Person person = newFrischBestellung.getPerson() == null ?
                oldFrischBestellung.getPerson()
                : toPersonMapper.apply(newFrischBestellung.getPerson());

        return new FrischBestellung(
                oldFrischBestellung.getId(),
                newFrischBestand.orElseGet(oldFrischBestellung::getFrischbestand),
                person,
                newFrischBestellung.getDate()
        );
    }
}

