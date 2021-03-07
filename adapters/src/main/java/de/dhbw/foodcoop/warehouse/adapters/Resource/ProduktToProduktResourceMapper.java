package de.dhbw.foodcoop.warehouse.adapters.Resource;

import de.dhbw.foodcoop.warehouse.adapters.Row.ProduktRow;
import de.dhbw.foodcoop.warehouse.domain.entities.Lagerbestand;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProduktToProduktResourceMapper implements Function<Produkt, ProduktResource> {
    private LagerbestandToLagerbestandResourceMapper lagerbestandToLagerbestandResourceMapper;

    @Autowired
    public ProduktToProduktResourceMapper(LagerbestandToLagerbestandResourceMapper lagerbestandToLagerbestandResourceMapper) {
        this.lagerbestandToLagerbestandResourceMapper = lagerbestandToLagerbestandResourceMapper;
    }

    @Override
    public ProduktResource apply(Produkt produkt) {
        return map(produkt);
    }

    private ProduktResource map(Produkt produkt) {
        return new ProduktResource(
                produkt.getId(),
                produkt.getName(),
                produkt.getKategorie().getName(),
                lagerbestandToLagerbestandResourceMapper.apply(produkt.getLagerbestand()));
    }
}
