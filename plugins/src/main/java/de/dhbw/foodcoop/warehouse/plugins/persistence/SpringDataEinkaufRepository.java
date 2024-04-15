package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.EinkaufEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;

public interface SpringDataEinkaufRepository extends JpaRepository<EinkaufEntity, String>{

	   @Query("SELECT b FROM EinkaufEntity b WHERE b.personId = :person_id")
	    List<EinkaufEntity> findAllFromPerson(@Param("person_id") String person_id);
	   
	    @Query("SELECT f FROM EinkaufEntity f WHERE f.date <= :date AND f.personId = :person_id")
	    List<EinkaufEntity> findByDateBetween(@Param("date") LocalDateTime date,  @Param("person_id") String person_id);
}
