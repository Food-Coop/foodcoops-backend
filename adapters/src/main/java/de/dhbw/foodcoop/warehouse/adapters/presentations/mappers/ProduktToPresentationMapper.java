package de.dhbw.foodcoop.warehouse.adapters.presentations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.presentations.ProduktPresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProduktToPresentationMapper implements Function<Produkt, ProduktPresentation> {
    @Override
    public ProduktPresentation apply(Produkt produkt) {
        return new ProduktPresentation(
                produkt.getId(),
                produkt.getName(),
                produkt.getKategorie().getId(),
                produkt.getLagerbestand()
        );
    }
}
