package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.EinheitRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.LagerbestandRepresentation;
import de.dhbw.foodcoop.warehouse.domain.values.Lagerbestand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class LagerbestandToRepresentationMapper implements Function<Lagerbestand, LagerbestandRepresentation> {
    private final EinheitToRepresentationMapper einheitToRepresentationMapper;

    @Autowired
    public LagerbestandToRepresentationMapper(EinheitToRepresentationMapper einheitToRepresentationMapper) {
        this.einheitToRepresentationMapper = einheitToRepresentationMapper;
    }

    @Override
    public LagerbestandRepresentation apply(Lagerbestand lagerbestand) {
        EinheitRepresentation einheitRepresentation = einheitToRepresentationMapper.apply(lagerbestand.getEinheit());
        return new LagerbestandRepresentation(
                einheitRepresentation
                , lagerbestand.getIstLagerbestand()
                , lagerbestand.getSollLagerbestand());
    }
}
