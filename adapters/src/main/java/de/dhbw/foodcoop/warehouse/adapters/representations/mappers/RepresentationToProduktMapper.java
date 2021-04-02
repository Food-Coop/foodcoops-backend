package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.ProduktRepresentation;
import de.dhbw.foodcoop.warehouse.application.LagerService.KategorieService;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.repositories.exceptions.KategorieNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
                kategorieService.findById(produktRepresentation.getKategorie()).orElseThrow(() -> new KategorieNotFoundException(produktRepresentation.getId())),
                produktRepresentation.getLagerbestand());
    }
}
