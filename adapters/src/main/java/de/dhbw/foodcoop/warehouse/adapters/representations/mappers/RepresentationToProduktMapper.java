package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.ProduktRepresentation;
import de.dhbw.foodcoop.warehouse.application.lager.KategorieService;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.exceptions.KategorieNotFoundException;
import de.dhbw.foodcoop.warehouse.domain.values.Lagerbestand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class RepresentationToProduktMapper implements Function<ProduktRepresentation, Produkt> {
    private final KategorieService kategorieService;
    private final RepresentationToLagerbestandMapper toLagerbestandMapper;

    @Autowired
    public RepresentationToProduktMapper(KategorieService kategorieService, RepresentationToLagerbestandMapper toLagerbestandMapper) {
        this.kategorieService = kategorieService;
        this.toLagerbestandMapper = toLagerbestandMapper;
    }

    @Override
    public Produkt apply(ProduktRepresentation produktRepresentation) {
        Kategorie kategorie = kategorieService.findById(produktRepresentation.getKategorie()).orElseThrow
                (() -> new KategorieNotFoundException(produktRepresentation.getId()));
        Lagerbestand lagerbestand = toLagerbestandMapper.apply(produktRepresentation.getLagerbestand());
        return new Produkt(produktRepresentation.getId(),
                produktRepresentation.getName(),
                kategorie,
                lagerbestand);
    }

    public Produkt update(Produkt oldProdukt, ProduktRepresentation newProdukt) {
        Optional<Kategorie> newKategorie = newProdukt.getKategorie() == null ||
                newProdukt.getKategorie().isBlank() ||
                newProdukt.getKategorie().equals("undefined") ?
                Optional.empty() :
                kategorieService.findById(newProdukt.getKategorie());
        Lagerbestand lagerbestand = newProdukt.getLagerbestand() == null ?
                oldProdukt.getLagerbestand()
                : toLagerbestandMapper.apply(newProdukt.getLagerbestand());

        return new Produkt(
                oldProdukt.getId(),
                newProdukt.getName() == null || newProdukt.getName().equals("undefined") ?
                        oldProdukt.getName() : newProdukt.getName(),
                newKategorie.orElseGet(oldProdukt::getKategorie),
                lagerbestand
        );
    }
}
