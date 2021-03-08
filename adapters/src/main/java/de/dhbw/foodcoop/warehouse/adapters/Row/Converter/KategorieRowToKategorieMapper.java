package de.dhbw.foodcoop.warehouse.adapters.Row.Converter;

import de.dhbw.foodcoop.warehouse.adapters.Row.KategorieRow;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class KategorieRowToKategorieMapper implements Function<KategorieRow, Kategorie> {
    @Override
    public Kategorie apply(KategorieRow kategorieRow) {
        return map(kategorieRow);
    }

    private Kategorie map(KategorieRow kategorieRow) {
        return new Kategorie(kategorieRow.getId(), kategorieRow.getName(), kategorieRow.getIcon());
    }
}
