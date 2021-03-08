package de.dhbw.foodcoop.warehouse.plugins.persistence;

import de.dhbw.foodcoop.warehouse.adapters.Row.ProduktRow;
import de.dhbw.foodcoop.warehouse.adapters.Row.Converter.ProduktRowToProduktMapper;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.repositories.ProduktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProduktRowRepository implements ProduktRepository {

    private final SpringDataProduktRowRepository springDataProduktRowRepository;
    private final ProduktRowToProduktMapper mapper;


    @Autowired
    public ProduktRowRepository(SpringDataProduktRowRepository springDataProduktRowRepository,
                                ProduktRowToProduktMapper mapper) {
        this.springDataProduktRowRepository = springDataProduktRowRepository;
        this.mapper = mapper;
    }

    @Override
    public Produkt produktAnlegen(Produkt produkt) {
        return null;
    }

    @Override
    public Produkt produktAktualisieren(Produkt produkt) {
        return null;
    }

    @Override
    public Produkt produktLoeschen(Produkt produkt) {
        return null;
    }

    @Override
    public List<Produkt> alleProdukteAbrufen() {
        List<ProduktRow> produktRows = springDataProduktRowRepository.findAll();
        return produktRows.stream().map(mapper).collect(Collectors.toList());
    }
}
