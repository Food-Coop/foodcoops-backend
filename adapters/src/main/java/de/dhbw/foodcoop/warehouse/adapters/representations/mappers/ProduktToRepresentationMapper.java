package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.LagerbestandRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.ProduktRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProduktToRepresentationMapper implements Function<Produkt, ProduktRepresentation> {
    private final LagerbestandToRepresentationMapper lagerbestandToRepresentationMapper;

    @Autowired
    public ProduktToRepresentationMapper(LagerbestandToRepresentationMapper lagerbestandToRepresentationMapper) {
        this.lagerbestandToRepresentationMapper = lagerbestandToRepresentationMapper;
    }

    @Override
    public ProduktRepresentation apply(Produkt produkt) {
        LagerbestandRepresentation lagerbestandRepresentation =
                lagerbestandToRepresentationMapper.apply(produkt.getLagerbestand());
        return new ProduktRepresentation(
                produkt.getId(),
                produkt.getName(),
                produkt.getKategorie().getId(),
                lagerbestandRepresentation
        );
    }
}
