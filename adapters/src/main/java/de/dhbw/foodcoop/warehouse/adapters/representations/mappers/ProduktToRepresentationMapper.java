package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.ProduktRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProduktToRepresentationMapper implements Function<Produkt, ProduktRepresentation> {
    @Override
    public ProduktRepresentation apply(Produkt produkt) {
        return new ProduktRepresentation(
                produkt.getId(),
                produkt.getName(),
                produkt.getKategorie().getId(),
                produkt.getLagerbestand()
        );
    }
}
