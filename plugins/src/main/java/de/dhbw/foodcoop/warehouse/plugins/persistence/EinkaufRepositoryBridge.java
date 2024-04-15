package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.dhbw.foodcoop.warehouse.domain.entities.EinkaufEntity;
import de.dhbw.foodcoop.warehouse.domain.repositories.EinkaufRepository;
@Repository
public class EinkaufRepositoryBridge implements EinkaufRepository {
	
    private final SpringDataEinkaufRepository springDataEinkaufRepository;

    @Autowired
    public EinkaufRepositoryBridge(SpringDataEinkaufRepository springDataEinkaufRepository) {
        this.springDataEinkaufRepository = springDataEinkaufRepository;
    }

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		springDataEinkaufRepository.deleteById(id);
		
	}

	@Override
	public List<EinkaufEntity> alle() {
		// TODO Auto-generated method stub
		return springDataEinkaufRepository.findAll();
	}

	@Override
	public List<EinkaufEntity> alleVonPerson(String person_id) {
		// TODO Auto-generated method stub
		return springDataEinkaufRepository.findAllFromPerson(person_id);
	}

	@Override
	public EinkaufEntity speichern(EinkaufEntity einkauf) {
		// TODO Auto-generated method stub
		return springDataEinkaufRepository.save(einkauf);
	}

	@Override
	public Optional<EinkaufEntity> findeMitId(String id) {
		// TODO Auto-generated method stub
		return springDataEinkaufRepository.findById(id);
	}

	@Override
	public List<EinkaufEntity> alleDazwischenVonPerson(LocalDateTime date1, LocalDateTime date2, String person) {
		return springDataEinkaufRepository.findByDateBetween(date1, date2, person);
	}
	

}
