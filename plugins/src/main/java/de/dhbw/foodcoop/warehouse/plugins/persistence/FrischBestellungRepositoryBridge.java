package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.repositories.FrischBestellungRepository;

@Repository
public class FrischBestellungRepositoryBridge implements FrischBestellungRepository{
    private final SpringDataFrischBestellungRepository springDataFrischBestellungRepository;

    @Autowired
    public FrischBestellungRepositoryBridge(SpringDataFrischBestellungRepository springDataFrischBestellungRepository) {
        this.springDataFrischBestellungRepository = springDataFrischBestellungRepository;
    }

    @Override
    public List<FrischBestellung> alle() {
        return springDataFrischBestellungRepository.findAll();
    }

    @Override
    public List<FrischBestellung> findeMitDatumNach(Timestamp date){
        return springDataFrischBestellungRepository.findByDateAfter(date);
    }

    @Override
    public List<FrischBestellung> findeMitDatumNachUndSum(Timestamp date){
        return springDataFrischBestellungRepository.findByDateAfterAndSum(date);
    }

    @Override
    public FrischBestellung speichern(FrischBestellung frischBestellung) {
        return springDataFrischBestellungRepository.save(frischBestellung);
    }

    @Override
    public Optional<FrischBestellung> findeMitId(String id) {
        return springDataFrischBestellungRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        springDataFrischBestellungRepository.deleteById(id);
    }
}
