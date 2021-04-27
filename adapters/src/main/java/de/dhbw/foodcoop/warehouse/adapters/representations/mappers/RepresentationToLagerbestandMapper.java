package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.LagerbestandRepresentation;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import de.dhbw.foodcoop.warehouse.domain.values.Lagerbestand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RepresentationToLagerbestandMapper implements Function<LagerbestandRepresentation, Lagerbestand> {
    private final RepresentationToEinheitMapper toEinheitMapper;

    @Autowired
    public RepresentationToLagerbestandMapper(RepresentationToEinheitMapper toEinheitMapper) {
        this.toEinheitMapper = toEinheitMapper;
    }

    @Override
    public Lagerbestand apply(LagerbestandRepresentation lagerbestandRepresentation) {
        Einheit einheit = toEinheitMapper.applyById(lagerbestandRepresentation.getEinheitRepresentation().getId());
        return new Lagerbestand(
                einheit
                , lagerbestandRepresentation.getIstLagerbestand()
                , lagerbestandRepresentation.getSollLagerbestand());
    }
}
