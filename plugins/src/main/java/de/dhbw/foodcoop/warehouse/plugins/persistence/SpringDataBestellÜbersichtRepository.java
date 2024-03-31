package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.dhbw.foodcoop.warehouse.domain.entities.BestellUebersicht;
import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;

public interface SpringDataBestellÜbersichtRepository extends JpaRepository<BestellUebersicht, String> {

	 @Query("SELECT b FROM BestellUebersicht b WHERE b.toOrderWithinDeadline.id = :deadlineId")
	 BestellUebersicht findByDeadline(@Param("deadlineId") String deadlineId);
}
