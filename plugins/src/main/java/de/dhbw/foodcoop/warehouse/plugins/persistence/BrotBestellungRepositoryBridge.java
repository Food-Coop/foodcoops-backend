package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.repositories.BrotBestellungRepository;

@Repository
public class BrotBestellungRepositoryBridge implements BrotBestellungRepository{
    private final SpringDataBrotBestellungRepository springDataBrotBestellungRepository;

    @Autowired
    public BrotBestellungRepositoryBridge(SpringDataBrotBestellungRepository springDataBrotBestellungRepository) {
        this.springDataBrotBestellungRepository = springDataBrotBestellungRepository;
    }

    @Override
    public List<BrotBestellung> alle() {
        return springDataBrotBestellungRepository.findAll();
    }

    @Override
    public List<BrotBestellung> findeMitDatumNachUndPerson(Timestamp date, String person_id){
        return springDataBrotBestellungRepository.findByDateAfterAndPerson(date, person_id);
    }

    @Override
    public List<BrotBestellung> findeMitDatumNachUndSum(Timestamp date){
        return springDataBrotBestellungRepository.findByDateAfterAndSum(date);
    }

    @Override
    public List<BrotBestellung> findeMitDatumZwischen(Timestamp date1, Timestamp date2, String person_id){
        return springDataBrotBestellungRepository.findByDateBetween(date1, date2, person_id);
    }

    @Override
    public BrotBestellung speichern(BrotBestellung brotBestellung) {
        return springDataBrotBestellungRepository.save(brotBestellung);
    }

    @Override
    public Optional<BrotBestellung> findeMitId(String id) {
        return springDataBrotBestellungRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        springDataBrotBestellungRepository.deleteById(id);
    }
}
