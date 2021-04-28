package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.KategorieRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.values.Icon;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class RepresentationToKategorieMapper implements Function<KategorieRepresentation, Kategorie> {

    @Override
    public Kategorie apply(KategorieRepresentation kategorieRepresentation) {

        return new Kategorie(
                kategorieRepresentation.getId(),
                kategorieRepresentation.getName(),
                new Icon(kategorieRepresentation.getIcon()),
                List.of());
    }

    public Kategorie update(Kategorie oldKategorie, KategorieRepresentation newKategorie) {

        return new Kategorie(
                oldKategorie.getId(),
                pickNewIfDefined(oldKategorie.getName(), newKategorie.getName()),
                new Icon(pickNewIfDefined(oldKategorie.getIcon().getIcon(), newKategorie.getIcon())),
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
