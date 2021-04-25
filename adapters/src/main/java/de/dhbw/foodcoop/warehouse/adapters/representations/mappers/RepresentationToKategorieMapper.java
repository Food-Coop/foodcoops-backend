package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.KategorieRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.ProduktRepresentation;
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

    @Override
    public Kategorie apply(KategorieRepresentation kategorieRepresentation) {

        return new Kategorie(
                kategorieRepresentation.getId(),
                kategorieRepresentation.getName(),
                kategorieRepresentation.getIcon(),
                List.of());
    }

    public Kategorie update(Kategorie oldKategorie, KategorieRepresentation newKategorie) {

        return new Kategorie(
                oldKategorie.getId(),
                pickNewIfDefined(oldKategorie.getName(), newKategorie.getName()),
                pickNewIfDefined(oldKategorie.getIcon(), newKategorie.getIcon()),
                List.of()
        );
    }

    private String pickNewIfDefined(String oldValue, String newValue) {
        return replaceNullWithUndefined(newValue).equals("undefined") ? oldValue : newValue;
    }

    private String replaceNullWithUndefined(String oldValue) {
        return oldValue == null ? "undefined" : oldValue;
    }
}
