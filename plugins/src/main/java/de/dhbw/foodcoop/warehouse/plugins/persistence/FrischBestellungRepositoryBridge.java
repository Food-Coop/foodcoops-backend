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
    public List<FrischBestellung> findeMitDatumNachUndPerson(Timestamp date, String person_id){
        return springDataFrischBestellungRepository.findByDateAfterAndPerson(date, person_id);
    }

    @Override
    public List<FrischBestellung> findeMitDatumNachUndSum(Timestamp date){
        return springDataFrischBestellungRepository.findByDateAfterAndSum(date);
    }

    @Override
    public List<FrischBestellung> findeMitDatumZwischen(Timestamp date1, Timestamp date2, String person_id){
        return springDataFrischBestellungRepository.findByDateBetween(date1, date2, person_id);
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

	@Override
	public List<FrischBestellung> findeAlleVonPerson(String person_id) {
		return springDataFrischBestellungRepository.findAllFromPerson(person_id);
	}

	@Override
	public List<FrischBestellung> findeAlleBestellungenNachDatum(Timestamp datum) {
		return springDataFrischBestellungRepository.findAllAfter(datum);
	}
}
