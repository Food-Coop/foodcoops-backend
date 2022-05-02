package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;

public interface SpringDataDeadlineRepository extends JpaRepository<Deadline, String> {

    @Query("SELECT d FROM Deadline d ORDER BY d.datum DESC")
    List<Deadline> findLast();

    // MariaDB: SELECT * FROM deadline ORDER BY datum DESC LIMIT 1;
}
