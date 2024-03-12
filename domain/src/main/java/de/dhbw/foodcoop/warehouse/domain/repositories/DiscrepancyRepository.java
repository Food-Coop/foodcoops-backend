package de.dhbw.foodcoop.warehouse.domain.repositories;

import java.util.List;
import java.util.Optional;

import de.dhbw.foodcoop.warehouse.domain.entities.DiscrepancyEntity;

public interface DiscrepancyRepository {

	public List<DiscrepancyEntity> alle();
	
	public Optional<DiscrepancyEntity> findeMitId(String id);
	
	public DiscrepancyEntity speichern(DiscrepancyEntity entity);
	
	public void deleteById(String id);
}
