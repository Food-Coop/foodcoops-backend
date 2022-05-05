package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.ProduktRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.values.Lagerbestand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RepresentationToProduktMapper implements Function<ProduktRepresentation, Produkt> {
    private final RepresentationToLagerbestandMapper toLagerbestandMapper;
    private final RepresentationToKategorieMapper toKategorieMapper;

    @Autowired
    public RepresentationToProduktMapper(RepresentationToLagerbestandMapper toLagerbestandMapper, RepresentationToKategorieMapper toKategorieMapper) {
        this.toLagerbestandMapper = toLagerbestandMapper;
        this.toKategorieMapper = toKategorieMapper;
    }

    @Override
    public Produkt apply(ProduktRepresentation produktRepresentation) {
        Kategorie kategorie = toKategorieMapper.apply(produktRepresentation.getKategorie());
        Lagerbestand lagerbestand = toLagerbestandMapper.apply(produktRepresentation.getLagerbestand());
        return new Produkt(produktRepresentation.getId(),
                produktRepresentation.getName(),
                kategorie,
                lagerbestand);
    }

    public Produkt update(Produkt oldProdukt, ProduktRepresentation newProdukt) {
        Kategorie kategorie = newProdukt.getKategorie() == null ?
                oldProdukt.getKategorie() 
                : toKategorieMapper.apply(newProdukt.getKategorie());
        Lagerbestand lagerbestand = newProdukt.getLagerbestand() == null ?
                oldProdukt.getLagerbestand()
                : toLagerbestandMapper.apply(newProdukt.getLagerbestand());

        return new Produkt(
                oldProdukt.getId(),
                newProdukt.getName() == null || newProdukt.getName().equals("undefined") ?
                        oldProdukt.getName() : newProdukt.getName(),
                kategorie,
                lagerbestand
        );
    }
}
