package de.dhbw.foodcoop.warehouse.adapters.Row.Mapper;

import de.dhbw.foodcoop.warehouse.adapters.Row.KategorieRow;
import de.dhbw.foodcoop.warehouse.adapters.Row.ProduktRow;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class KategorieToKategorieRowMapper implements Function<Kategorie, KategorieRow> {
    private final ProduktToProduktRowMapper produktToProduktRowMapper;

    @Autowired
    public KategorieToKategorieRowMapper(ProduktToProduktRowMapper produktToProduktRowMapper) {
        this.produktToProduktRowMapper = produktToProduktRowMapper;
    }

    @Override
    public KategorieRow apply(Kategorie kategorie) {
        return map(kategorie);
    }

    private KategorieRow map(Kategorie kategorie) {
        List<ProduktRow> produktRows = kategorie.getProdukte()
                .stream()
                .map(produktToProduktRowMapper)
                .collect(Collectors.toList());
        KategorieRow kategorieRow =
                new KategorieRow(kategorie.getId(), kategorie.getName(), kategorie.getIcon(), produktRows);
        produktRows.forEach(produktRow -> produktRow.setKategorie(kategorieRow));
        return kategorieRow;
    }
}
