package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.dhbw.foodcoop.warehouse.domain.entities.DiscrepancyEntity;
import de.dhbw.foodcoop.warehouse.domain.repositories.DiscrepancyRepository;

@Repository
public class DiscrepancyRepositoryBridge implements DiscrepancyRepository {

	
    private final SpringDataDiscrepancyRepository springDataDiscrepancyRepository;

    @Autowired
    public DiscrepancyRepositoryBridge(SpringDataDiscrepancyRepository springDataDiscrepancyRepository) {
        this.springDataDiscrepancyRepository = springDataDiscrepancyRepository;
    }
    
	@Override
	public List<DiscrepancyEntity> alle() {
		return springDataDiscrepancyRepository.findAll();
	}

	@Override
	public Optional<DiscrepancyEntity> findeMitId(String id) {
		return springDataDiscrepancyRepository.findById(id);
	}

	@Override
	public DiscrepancyEntity speichern(DiscrepancyEntity entity) {
		return springDataDiscrepancyRepository.save(entity);
	}

	@Override
	public void deleteById(String id) {
		springDataDiscrepancyRepository.deleteById(id);
	}

}
