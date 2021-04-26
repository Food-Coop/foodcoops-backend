package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.EinheitRepresentation;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RepresentationToEinheitMapper implements Function<EinheitRepresentation, Einheit> {
    @Override
    public Einheit apply(EinheitRepresentation einheitRepresentation) {
        return new Einheit(einheitRepresentation.getId(), einheitRepresentation.getName());
    }
}
