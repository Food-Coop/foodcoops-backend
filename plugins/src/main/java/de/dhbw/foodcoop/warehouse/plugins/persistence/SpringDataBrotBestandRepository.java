package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;

public interface SpringDataBrotBestandRepository extends JpaRepository<BrotBestand, String> {
	public List<BrotBestand> findAllByOrderByIdAsc();
}
