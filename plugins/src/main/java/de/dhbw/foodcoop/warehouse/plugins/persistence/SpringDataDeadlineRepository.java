package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;

public interface SpringDataDeadlineRepository extends JpaRepository<Deadline, String> {

    @Query(value="SELECT * FROM deadline d ORDER BY d.datum DESC LIMIT 1", nativeQuery = true)
    Optional<Deadline> findLast();

    // MariaDB: SELECT * FROM deadline ORDER BY datum DESC LIMIT 1;
    
    @Query(value = "SELECT * FROM deadline d ORDER BY d.datum DESC LIMIT :position, 1", nativeQuery = true)
    Optional<Deadline> findFromSortedPosition(int position);
}
