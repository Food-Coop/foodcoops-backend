package de.dhbw.foodcoop.warehouse.adapters.Row.Mapper;

import de.dhbw.foodcoop.warehouse.adapters.Row.LagerbestandRow;
import de.dhbw.foodcoop.warehouse.adapters.Row.ProduktRow;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProduktToProduktRowMapper implements Function<Produkt, ProduktRow> {
    private final LagerbestandToLagerbestandRowMapper lagerbestandToLagerbestandRowMapper;

    @Autowired
    public ProduktToProduktRowMapper(LagerbestandToLagerbestandRowMapper lagerbestandToLagerbestandRowMapper) {
        this.lagerbestandToLagerbestandRowMapper = lagerbestandToLagerbestandRowMapper;
    }

    @Override
    public ProduktRow apply(Produkt produkt) {
        return map(produkt);
    }

    private ProduktRow map(Produkt produkt) {
        LagerbestandRow lagerbestandRow = lagerbestandToLagerbestandRowMapper.apply(produkt.getLagerbestand());
        return new ProduktRow(produkt.getId(), produkt.getName(), lagerbestandRow);
    }
}
