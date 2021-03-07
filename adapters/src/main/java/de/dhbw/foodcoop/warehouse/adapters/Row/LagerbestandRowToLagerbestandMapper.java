package de.dhbw.foodcoop.warehouse.adapters.Row;

import de.dhbw.foodcoop.warehouse.domain.entities.Lagerbestand;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import de.dhbw.foodcoop.warehouse.domain.values.Menge;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class LagerbestandRowToLagerbestandMapper implements Function<LagerbestandRow, Lagerbestand> {
    public LagerbestandRowToLagerbestandMapper() {
    }

    @Override
    public Lagerbestand apply(LagerbestandRow lagerbestandRow) {
        return map(lagerbestandRow);
    }

    private Lagerbestand map(LagerbestandRow lagerbestandRow) {
        return new Lagerbestand(
                lagerbestandRow.getId(),
                new Menge(new Einheit(lagerbestandRow.getEinheit()), lagerbestandRow.getIstLagerbestand()),
                new Menge(new Einheit(lagerbestandRow.getEinheit()), lagerbestandRow.getSollLagerbestand())
        );
    }
}
