package de.dhbw.foodcoop.warehouse.adapters.Row.Mapper;

import de.dhbw.foodcoop.warehouse.adapters.Row.ProduktRow;
import de.dhbw.foodcoop.warehouse.domain.entities.Lagerbestand;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
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
        Lagerbestand lagerbestand = lagerbestandRowToLagerbestandMapper.apply(produktRow.getLagerbestand());
        Produkt produkt = new Produkt(
                produktRow.getId(),
                produktRow.getName(),
                lagerbestand
        );
        lagerbestand.setProdukt(produkt);
        return produkt;
    }
}
