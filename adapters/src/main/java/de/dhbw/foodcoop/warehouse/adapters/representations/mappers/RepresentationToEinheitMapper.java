package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.EinheitRepresentation;
import de.dhbw.foodcoop.warehouse.application.lager.EinheitService;
import de.dhbw.foodcoop.warehouse.domain.exceptions.EinheitNotFoundException;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RepresentationToEinheitMapper implements Function<EinheitRepresentation, Einheit> {
    private final EinheitService service;

    @Autowired
    public RepresentationToEinheitMapper(EinheitService service) {
        this.service = service;
    }

    @Override
    public Einheit apply(EinheitRepresentation einheitRepresentation) {
        return new Einheit(einheitRepresentation.getId(), einheitRepresentation.getName());
    }

    public Einheit applyById(String einheitId) {
        return service.findById(einheitId).orElseThrow(() -> new EinheitNotFoundException(einheitId));
    }
}
