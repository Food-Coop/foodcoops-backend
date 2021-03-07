package de.dhbw.foodcoop.warehouse.adapters.Resource;

import de.dhbw.foodcoop.warehouse.domain.entities.Lagerbestand;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class LagerbestandToLagerbestandResourceMapper implements Function<Lagerbestand, LagerbestandResource> {
    public LagerbestandToLagerbestandResourceMapper() {
    }

    @Override
    public LagerbestandResource apply(Lagerbestand lagerbestand) {
        return map(lagerbestand);
    }

    private LagerbestandResource map(Lagerbestand lagerbestand) {
        return new LagerbestandResource(lagerbestand.getId(),
                lagerbestand.getIstLagerbestand().getMenge(),
                lagerbestand.getSollLagerbestand().getMenge(),
                lagerbestand.getIstLagerbestand().getEinheit().getName());
    }
}
