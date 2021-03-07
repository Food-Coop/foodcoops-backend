package de.dhbw.foodcoop.warehouse.adapters.Row;

import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.values.Kategorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProduktRowToProduktMapper implements Function<ProduktRow, Produkt> {
    private final LagerbestandRowToLagerbestandMapper lagerbestandRowToLagerbestandMapper;

    @Autowired
    public ProduktRowToProduktMapper(LagerbestandRowToLagerbestandMapper lagerbestandRowToLagerbestandMapper) {
        this.lagerbestandRowToLagerbestandMapper = lagerbestandRowToLagerbestandMapper;
    }

    @Override
    public Produkt apply(ProduktRow produktRow) {
        return map(produktRow);
    }

    private Produkt map(ProduktRow produktRow) {
        return new Produkt(
                produktRow.getId(),
                produktRow.getName(),
                new Kategorie(produktRow.getKategorie()),
                lagerbestandRowToLagerbestandMapper.apply(produktRow.getLagerbestand())
        );
    }
}
