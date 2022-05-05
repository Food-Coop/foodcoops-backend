package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestandRepresentation;
import de.dhbw.foodcoop.warehouse.application.lager.EinheitService;
import de.dhbw.foodcoop.warehouse.application.lager.KategorieService;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.exceptions.EinheitNotFoundException;
import de.dhbw.foodcoop.warehouse.domain.exceptions.KategorieNotFoundException;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;

@Component
public class RepresentationToFrischBestandMapper implements Function<FrischBestandRepresentation, FrischBestand> {

    private final EinheitService einheitService;
    private final KategorieService kategorieService;
    private final RepresentationToEinheitMapper repToEinMapper;
    private final RepresentationToKategorieMapper repToKatMapper;

    @Autowired
    public RepresentationToFrischBestandMapper(EinheitService einheitService, KategorieService kategorieService, RepresentationToEinheitMapper repToEinMapper, RepresentationToKategorieMapper repToKatMapper) {
        this.einheitService = einheitService;
        this.kategorieService = kategorieService;
        this.repToEinMapper = repToEinMapper;
        this.repToKatMapper = repToKatMapper;
    }

    @Override
    public FrischBestand apply(FrischBestandRepresentation frischBestandRepresentation) {
        Einheit einheit = einheitService.findById(frischBestandRepresentation.getEinheit().getId()).orElseThrow
                (() -> new EinheitNotFoundException(frischBestandRepresentation.getEinheit().getId()));
        Kategorie kategorie = kategorieService.findById(frischBestandRepresentation.getKategorie().getId()).orElseThrow
                (() -> new KategorieNotFoundException(frischBestandRepresentation.getKategorie().getId()));
        return new FrischBestand(
            frischBestandRepresentation.getId(),
            frischBestandRepresentation.getName(),
            frischBestandRepresentation.getVerfuegbarkeit(),
            frischBestandRepresentation.getHerkunftsland(),
            frischBestandRepresentation.getGebindegroesse(),
            einheit,
            kategorie,
            frischBestandRepresentation.getPreis());
    }

    public FrischBestand update(FrischBestand oldFrischBestand, FrischBestandRepresentation newFrischBestand) {
        Einheit einheit = newFrischBestand.getEinheit() == null ? oldFrischBestand.getEinheit() : repToEinMapper.apply(newFrischBestand.getEinheit());
        Kategorie kategorie = newFrischBestand.getKategorie() == null ? oldFrischBestand.getKategorie() : repToKatMapper.apply(newFrischBestand.getKategorie());
        return new FrischBestand(
                oldFrischBestand.getId(),
                pickNewIfDefined(oldFrischBestand.getName(), newFrischBestand.getName()),
                newFrischBestand.getVerfuegbarkeit(),
                pickNewIfDefined(oldFrischBestand.getHerkunftsland(), newFrischBestand.getHerkunftsland()),
                newFrischBestand.getGebindegroesse(),
                einheit,
                kategorie,
                newFrischBestand.getPreis()
        );
    }

    private String pickNewIfDefined(String oldValue, String newValue) {
        return replaceNullWithUndefined(newValue).equals("undefined") ? oldValue : newValue;
    }

    private String replaceNullWithUndefined(String oldValue) {
        return oldValue == null ? "undefined" : oldValue;
    }
}
