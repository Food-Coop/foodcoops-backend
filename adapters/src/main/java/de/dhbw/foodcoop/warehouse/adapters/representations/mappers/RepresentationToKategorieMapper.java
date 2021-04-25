package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.KategorieRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RepresentationToKategorieMapper implements Function<KategorieRepresentation, Kategorie> {
    private final RepresentationToProduktMapper produktMapper;

    @Autowired
    public RepresentationToKategorieMapper(RepresentationToProduktMapper produktMapper) {
        this.produktMapper = produktMapper;
    }

    @Override
    public Kategorie apply(KategorieRepresentation kategorieRepresentation) {

        List<Produkt> produkte = List.of();
        if (kategorieRepresentation.getProdukte() != null) {
            produkte = kategorieRepresentation.getProdukte().stream()
                    .map(produktMapper).collect(Collectors.toList());
        }
        return new Kategorie(
                replaceNullWithUndefined(kategorieRepresentation.getId()),
                replaceNullWithUndefined(kategorieRepresentation.getName()),
                replaceNullWithUndefined(kategorieRepresentation.getIcon()),
                produkte);
    }

    private String replaceNullWithUndefined(String oldValue) {
        return oldValue == null ? "undefined" : oldValue;
    }
}
