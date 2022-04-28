package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestandRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;

@Component
public class FrischBestandToRepresentationMapper implements Function<FrischBestand, FrischBestandRepresentation> {
   
    @Autowired
    public FrischBestandToRepresentationMapper(EinheitToRepresentationMapper mapper) {
    }

    @Override
    public FrischBestandRepresentation apply(FrischBestand frischBestand) {
        Einheit einheit = frischBestand.getEinheit();
        Kategorie kategorie = frischBestand.getKategorie();
        return new FrischBestandRepresentation(frischBestand.getId(),
                frischBestand.getName(),
                frischBestand.getVerfuegbarkeit(),
                frischBestand.getHerkunftsland(),
                frischBestand.getGebindegroesse(),
                einheit,
                kategorie,
                frischBestand.getPreis());
    }
}
