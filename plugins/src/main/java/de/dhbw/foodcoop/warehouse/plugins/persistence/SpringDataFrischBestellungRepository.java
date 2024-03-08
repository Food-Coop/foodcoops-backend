package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.dhbw.foodcoop.warehouse.domain.entities.Person;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;

public interface SpringDataFrischBestellungRepository extends JpaRepository<FrischBestellung, String>{

    @Query("SELECT f FROM FrischBestellung f WHERE f.datum > :date AND f.personId = :person_id")
    List<FrischBestellung> findByDateAfterAndPerson(@Param("date") Timestamp date, @Param("person_id") String person_id);

    @Query("SELECT f FROM FrischBestellung f WHERE f.datum <= :date1 AND f.datum > :date2 AND f.personId = :person_id")
    List<FrischBestellung> findByDateBetween(@Param("date1") Timestamp date1, @Param("date2") Timestamp date2, @Param("person_id") String person_id);
    
    @Query("SELECT new FrischBestellung(f.id, f.personId, f.frischbestand, SUM(f.bestellmenge)) " +
            "FROM FrischBestellung f " +
            "WHERE f.datum > :date " +
            "GROUP BY f.frischbestand " +
            "ORDER BY f.frischbestand.kategorie.name"
    )
    List<FrischBestellung> findByDateAfterAndSum(@Param("date") Timestamp date);

    
    @Query("SELECT f FROM FrischBestellung f WHERE f.personId = :person_id")
    List<FrischBestellung> findAllFromPerson(@Param("person_id") String person_id);
}
