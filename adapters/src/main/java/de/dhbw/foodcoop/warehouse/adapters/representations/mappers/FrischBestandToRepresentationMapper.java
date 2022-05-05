package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import de.dhbw.foodcoop.warehouse.adapters.representations.EinheitRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.KategorieRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestandRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;

@Component
public class FrischBestandToRepresentationMapper implements Function<FrischBestand, FrischBestandRepresentation> {

    private final EinheitToRepresentationMapper einheitMapper;
    private final KategorieToRepresentationMapper kategorieMapper;

    @Lazy
    @Autowired
    public FrischBestandToRepresentationMapper(EinheitToRepresentationMapper eMapper, KategorieToRepresentationMapper kMapper) {
        this.einheitMapper = eMapper;
        this. kategorieMapper = kMapper;
    }

    @Override
    public FrischBestandRepresentation apply(FrischBestand frischBestand) {
        EinheitRepresentation einheit = einheitMapper.apply(frischBestand.getEinheit());
        KategorieRepresentation kategorie = kategorieMapper.apply(frischBestand.getKategorie());
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
