package de.dhbw.foodcoop.warehouse.adapters.Row.Mapper;

import de.dhbw.foodcoop.warehouse.adapters.Row.KategorieRow;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class KategorieRowToKategorieMapper implements Function<KategorieRow, Kategorie> {
    private final ProduktRowToProduktMapper produktMapper;

    @Autowired
    public KategorieRowToKategorieMapper(ProduktRowToProduktMapper produktMapper) {
        this.produktMapper = produktMapper;
    }

    @Override
    public Kategorie apply(KategorieRow kategorieRow) {
        return map(kategorieRow);
    }

    private Kategorie map(KategorieRow kategorieRow) {
        List<Produkt> produkte = kategorieRow.getProduktRows().stream().map(produktMapper).collect(Collectors.toList());
        Kategorie kategorie = new Kategorie(kategorieRow.getId(),
                kategorieRow.getName(),
                kategorieRow.getIcon(),
                produkte);
        produkte.forEach(produkt -> produkt.setKategorie(kategorie));
        return kategorie;
    }
}
