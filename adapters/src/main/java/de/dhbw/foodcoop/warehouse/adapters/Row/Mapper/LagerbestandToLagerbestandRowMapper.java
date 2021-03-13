package de.dhbw.foodcoop.warehouse.adapters.Row.Mapper;

import de.dhbw.foodcoop.warehouse.adapters.Row.LagerbestandRow;
import de.dhbw.foodcoop.warehouse.domain.entities.Lagerbestand;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class LagerbestandToLagerbestandRowMapper implements Function<Lagerbestand, LagerbestandRow> {
    public LagerbestandRow apply(Lagerbestand lagerbestand) {
        return map(lagerbestand);
    }

    private LagerbestandRow map(Lagerbestand lagerbestand) {
        return new LagerbestandRow(lagerbestand.getId(),
                lagerbestand.getIstLagerbestand().getMenge(),
                lagerbestand.getSollLagerbestand().getMenge(),
                lagerbestand.getIstLagerbestand().getEinheit().getName());
    }
}
