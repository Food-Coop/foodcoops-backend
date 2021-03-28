package de.dhbw.foodcoop.warehouse.adapters.presentations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.presentations.ProduktPresentation;
import de.dhbw.foodcoop.warehouse.application.LagerService.KategorieService;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.repositories.exceptions.KategorieNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PresentationToProduktMapper implements Function<ProduktPresentation, Produkt> {
    private final KategorieService kategorieService;

    @Autowired
    public PresentationToProduktMapper(KategorieService kategorieService) {
        this.kategorieService = kategorieService;
    }

    @Override
    public Produkt apply(ProduktPresentation produktPresentation) {
        return new Produkt(produktPresentation.getId(),
                produktPresentation.getName(),
                kategorieService.findById(produktPresentation.getKategorie()).orElseThrow(() -> new KategorieNotFoundException(produktPresentation.getId())),
                produktPresentation.getLagerbestand());
    }
}
