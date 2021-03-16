package de.dhbw.foodcoop.warehouse.adapters.Resource.Mapper;

import de.dhbw.foodcoop.warehouse.adapters.Resource.KategorieResource;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class KategorieToKategorieResourceMapper implements Function<Kategorie, KategorieResource> {
    private final ProduktToProduktResourceMapper produktToProduktResourceMapper;

    @Autowired
    public KategorieToKategorieResourceMapper(ProduktToProduktResourceMapper produktToProduktResourceMapper) {
        this.produktToProduktResourceMapper = produktToProduktResourceMapper;
    }

    @Override
    public KategorieResource apply(Kategorie kategorie) {
        return map(kategorie);
    }

    private KategorieResource map(Kategorie kategorie) {
        return new KategorieResource(kategorie.getId(),
                kategorie.getName(),
                kategorie.getIcon(),
                kategorie.getProdukte()
                        .stream()
                        .map(produktToProduktResourceMapper)
                        .collect(Collectors.toList()));
    }
}
