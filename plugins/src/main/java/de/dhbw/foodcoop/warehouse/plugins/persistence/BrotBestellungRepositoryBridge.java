package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.time.LocalDateTime;
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
    public List<BrotBestellung> findeMitDatumNachUndPerson(LocalDateTime date, String person_id){
        return springDataBrotBestellungRepository.findByDateAfterAndPerson(date, person_id);
    }

    @Override
    public List<BrotBestellung> findeMitDatumNachUndSum(LocalDateTime date){
        return springDataBrotBestellungRepository.findByDateAfterAndSum(date);
    }

    @Override
    public List<BrotBestellung> findeMitDatumZwischen(LocalDateTime date1, LocalDateTime date2, String person_id){
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

	@Override
	public List<BrotBestellung> alleVonPerson(String person_id) {
		return springDataBrotBestellungRepository.findAllFromPerson(person_id);
	}

	@Override
	public List<BrotBestellung> findAllOrdersAfterDate(LocalDateTime datum) {
		// TODO Auto-generated method stub
		return springDataBrotBestellungRepository.findAllAfter(datum);
	}

	@Override
	public List<BrotBestellung> findeMitDatumZwischen(LocalDateTime datum1, LocalDateTime datum2) {
		return springDataBrotBestellungRepository.findByDateBetween(datum1, datum2);
	}
}
