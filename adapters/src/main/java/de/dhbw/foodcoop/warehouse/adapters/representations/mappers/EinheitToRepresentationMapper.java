package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.EinheitRepresentation;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EinheitToRepresentationMapper implements Function<Einheit, EinheitRepresentation> {
    @Override
    public EinheitRepresentation apply(Einheit einheit) {
        return new EinheitRepresentation(einheit.getId(), einheit.getName());
    }
}
