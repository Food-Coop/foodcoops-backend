package de.dhbw.foodcoop.warehouse.plugins.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import de.dhbw.foodcoop.warehouse.domain.entities.DiscrepancyEntity;

public interface SpringDataDiscrepancyRepository extends JpaRepository<DiscrepancyEntity, String> {
	

}
