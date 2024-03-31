package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.dhbw.foodcoop.warehouse.domain.entities.BestellUebersicht;
import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;
import de.dhbw.foodcoop.warehouse.domain.repositories.BestellÜbersichtRepository;
@Repository
public class BestellÜbersichtRepositoryBridge implements BestellÜbersichtRepository{

	@Autowired
	private SpringDataBestellÜbersichtRepository repo;
	
	@Override
	public BestellUebersicht findeMitDeadline(Deadline deadline) {
		// TODO Auto-generated method stub
		return repo.findByDeadline(deadline.getId());
	}

	@Override
	public BestellUebersicht speichern(BestellUebersicht bestellÜbersicht) {
		// TODO Auto-generated method stub
		return repo.save(bestellÜbersicht);
	}

	@Override
	public Optional<BestellUebersicht> findeMitId(String id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public void deleteById(String id) {
		repo.deleteById(id);
	}
	
	

}
