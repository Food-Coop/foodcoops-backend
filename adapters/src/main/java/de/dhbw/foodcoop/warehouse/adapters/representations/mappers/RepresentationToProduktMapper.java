package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.ProduktRepresentation;
import de.dhbw.foodcoop.warehouse.application.lager.KategorieService;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.exceptions.KategorieNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class RepresentationToProduktMapper implements Function<ProduktRepresentation, Produkt> {
    private final KategorieService kategorieService;

    @Autowired
    public RepresentationToProduktMapper(KategorieService kategorieService) {
        this.kategorieService = kategorieService;
    }

    @Override
    public Produkt apply(ProduktRepresentation produktRepresentation) {
        return new Produkt(produktRepresentation.getId(),
                produktRepresentation.getName(),
                kategorieService.findById(produktRepresentation.getKategorie()).orElseThrow
                        (() -> new KategorieNotFoundException(produktRepresentation.getId())),
                produktRepresentation.getLagerbestand());
    }

    public Produkt update(Produkt oldProdukt, ProduktRepresentation newProdukt) {
        Optional<Kategorie> newKategorie = newProdukt.getKategorie() == null ||
                newProdukt.getKategorie().equals("undefined") ?
                Optional.empty() :
                kategorieService.findById(newProdukt.getKategorie());

        return new Produkt(
                oldProdukt.getId(),
                newProdukt.getName() == null || newProdukt.getName().equals("undefined") ?
                        oldProdukt.getName() : newProdukt.getName(),
                newKategorie.orElseGet(oldProdukt::getKategorie),
                newProdukt.getLagerbestand() == null ?
                        oldProdukt.getLagerbestand() : newProdukt.getLagerbestand()
        );
    }
}
