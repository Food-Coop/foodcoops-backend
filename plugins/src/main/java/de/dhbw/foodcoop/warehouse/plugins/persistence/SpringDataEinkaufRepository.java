package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.EinkaufEntity;

public interface SpringDataEinkaufRepository extends JpaRepository<EinkaufEntity, String>{

	   @Query("SELECT b FROM EinkaufEntity b WHERE b.personId = :person_id")
	    List<EinkaufEntity> findAllFromPerson(@Param("person_id") String person_id);
}
