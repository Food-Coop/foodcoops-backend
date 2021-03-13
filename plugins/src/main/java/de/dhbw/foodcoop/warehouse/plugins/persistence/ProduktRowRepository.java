package de.dhbw.foodcoop.warehouse.plugins.persistence;

import de.dhbw.foodcoop.warehouse.adapters.Row.Mapper.ProduktToProduktRowMapper;
import de.dhbw.foodcoop.warehouse.adapters.Row.ProduktRow;
import de.dhbw.foodcoop.warehouse.adapters.Row.Mapper.ProduktRowToProduktMapper;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.repositories.LagerbestandRepository;
import de.dhbw.foodcoop.warehouse.domain.repositories.ProduktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProduktRowRepository implements ProduktRepository {

    private final SpringDataProduktRowRepository springDataProduktRowRepository;
    private final LagerbestandRepository lagerbestandRepository;
    private final ProduktRowToProduktMapper produktRowToProduktMapper;
    private final ProduktToProduktRowMapper produktToProduktRowMapper;


    @Autowired
    public ProduktRowRepository(SpringDataProduktRowRepository springDataProduktRowRepository,
                                LagerbestandRepository lagerbestandRepository,
                                ProduktRowToProduktMapper produktRowToProduktMapper, ProduktToProduktRowMapper produktToProduktRowMapper) {
        this.springDataProduktRowRepository = springDataProduktRowRepository;
        this.lagerbestandRepository = lagerbestandRepository;
        this.produktRowToProduktMapper = produktRowToProduktMapper;
        this.produktToProduktRowMapper = produktToProduktRowMapper;
    }

    @Override
    public List<Produkt> alleProdukteAbrufen() {
        List<ProduktRow> produktRows = springDataProduktRowRepository.findAll();
        return produktRows.stream().map(produktRowToProduktMapper).collect(Collectors.toList());
    }

    @Override
    public Produkt speichern(Produkt produkt) {
        lagerbestandRepository.speichern(produkt.getLagerbestand());
        ProduktRow toSave = produktToProduktRowMapper.apply(produkt);
        ProduktRow isSaved = springDataProduktRowRepository.save(toSave);
        return produktRowToProduktMapper.apply(isSaved);
    }
}
