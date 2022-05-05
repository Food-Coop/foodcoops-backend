package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.KategorieRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.LagerbestandRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.ProduktRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProduktToRepresentationMapper implements Function<Produkt, ProduktRepresentation> {
    private final LagerbestandToRepresentationMapper lagerbestandToRepresentationMapper;
    private final KategorieToRepresentationMapper kategorieToRepresentationMapper;

    @Lazy
    @Autowired
    public ProduktToRepresentationMapper(LagerbestandToRepresentationMapper lagerbestandToRepresentationMapper, KategorieToRepresentationMapper kategorieToRepresentationMapper) {
        this.lagerbestandToRepresentationMapper = lagerbestandToRepresentationMapper;
        this.kategorieToRepresentationMapper = kategorieToRepresentationMapper;
    }

    @Override
    public ProduktRepresentation apply(Produkt produkt) {
        LagerbestandRepresentation lagerbestandRepresentation =
                lagerbestandToRepresentationMapper.apply(produkt.getLagerbestand());
        KategorieRepresentation kategorieRepresentation =
                kategorieToRepresentationMapper.apply(produkt.getKategorie());
        return new ProduktRepresentation(
                produkt.getId(),
                produkt.getName(),
                kategorieRepresentation,
                lagerbestandRepresentation
        );
    }
}
