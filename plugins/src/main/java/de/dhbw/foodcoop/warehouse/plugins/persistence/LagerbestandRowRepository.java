package de.dhbw.foodcoop.warehouse.plugins.persistence;

import de.dhbw.foodcoop.warehouse.adapters.Row.LagerbestandRow;
import de.dhbw.foodcoop.warehouse.adapters.Row.Mapper.LagerbestandRowToLagerbestandMapper;
import de.dhbw.foodcoop.warehouse.adapters.Row.Mapper.LagerbestandToLagerbestandRowMapper;
import de.dhbw.foodcoop.warehouse.domain.entities.Lagerbestand;
import de.dhbw.foodcoop.warehouse.domain.repositories.LagerbestandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LagerbestandRowRepository implements LagerbestandRepository {
    private final LagerbestandToLagerbestandRowMapper lagerbestandToLagerbestandRowMapper;
    private final LagerbestandRowToLagerbestandMapper lagerbestandRowToLagerbestandMapper;
    private final SpringDataLagerbestandRowRepository springDataLagerbestandRowRepository;

    @Autowired
    public LagerbestandRowRepository(LagerbestandToLagerbestandRowMapper lagerbestandToLagerbestandRowMapper,
                                     LagerbestandRowToLagerbestandMapper lagerbestandRowToLagerbestandMapper,
                                     SpringDataLagerbestandRowRepository springDataLagerbestandRowRepository) {
        this.lagerbestandToLagerbestandRowMapper = lagerbestandToLagerbestandRowMapper;
        this.lagerbestandRowToLagerbestandMapper = lagerbestandRowToLagerbestandMapper;
        this.springDataLagerbestandRowRepository = springDataLagerbestandRowRepository;
    }

    @Override
    public Lagerbestand speichern(Lagerbestand lagerbestand) {
        LagerbestandRow toSave = lagerbestandToLagerbestandRowMapper.apply(lagerbestand);
        LagerbestandRow isSaved = springDataLagerbestandRowRepository.save(toSave);
        return lagerbestandRowToLagerbestandMapper.apply(isSaved);
    }
}
