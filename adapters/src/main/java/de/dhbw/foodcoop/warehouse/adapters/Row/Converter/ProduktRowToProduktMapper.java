package de.dhbw.foodcoop.warehouse.adapters.Row.Converter;

import de.dhbw.foodcoop.warehouse.adapters.Row.ProduktRow;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Lagerbestand;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProduktRowToProduktMapper implements Function<ProduktRow, Produkt> {
    private final KategorieRowToKategorieMapper kategorieRowToKategorieMapper;
    private final LagerbestandRowToLagerbestandMapper lagerbestandRowToLagerbestandMapper;

    @Autowired
    public ProduktRowToProduktMapper(KategorieRowToKategorieMapper kategorieRowToKategorieMapper,
                                     LagerbestandRowToLagerbestandMapper lagerbestandRowToLagerbestandMapper) {
        this.kategorieRowToKategorieMapper = kategorieRowToKategorieMapper;
        this.lagerbestandRowToLagerbestandMapper = lagerbestandRowToLagerbestandMapper;
    }

    @Override
    public Produkt apply(ProduktRow produktRow) {
        return map(produktRow);
    }

    private Produkt map(ProduktRow produktRow) {
        Kategorie kategorie = kategorieRowToKategorieMapper.apply(produktRow.getKategorie());
        Lagerbestand lagerbestand = lagerbestandRowToLagerbestandMapper.apply(produktRow.getLagerbestand());
        Produkt produkt = new Produkt(
                produktRow.getId(),
                produktRow.getName(),
                kategorie,
                lagerbestand
        );
        lagerbestand.setProdukt(produkt);
        return produkt;
    }
}
