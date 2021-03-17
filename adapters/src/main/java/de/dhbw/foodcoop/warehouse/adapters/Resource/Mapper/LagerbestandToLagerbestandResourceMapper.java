package de.dhbw.foodcoop.warehouse.adapters.Resource.Mapper;

import de.dhbw.foodcoop.warehouse.adapters.Resource.LagerbestandResource;
import de.dhbw.foodcoop.warehouse.domain.values.Lagerbestand;
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
        return new LagerbestandResource(
                lagerbestand.getIstLagerbestand(),
                lagerbestand.getSollLagerbestand(),
                lagerbestand.getEinheit().getName());
    }
}
