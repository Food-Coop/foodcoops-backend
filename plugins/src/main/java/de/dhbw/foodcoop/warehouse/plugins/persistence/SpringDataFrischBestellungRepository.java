package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;

public interface SpringDataFrischBestellungRepository extends JpaRepository<FrischBestellung, String>{

    @Query("SELECT f FROM FrischBestellung f WHERE f.datum > :date AND f.person_id = :person_id")
    List<FrischBestellung> findByDateAfterAndPerson(@Param("date") Timestamp date, @Param("person_id") String person_id);

//    ****** NATIVE QUERY ******
//    @Query(value = "SELECT new FrischBestellung(f.id, f.frischbestand, f.person_id, SUM(f.bestellmenge)) " +
//            "FROM FrischBestellung f " +
//            "WHERE f.datum > :date " +
//            "GROUP BY f.frischbestand", nativeQuery = true)
//    List<FrischBestellung> findByDateAfterAndSum(@Param("date") Timestamp date);

    @Query("SELECT new FrischBestellung(f.id, f.person_id, f.frischbestand,  SUM(f.bestellmenge)) " +
            "FROM FrischBestellung f " +
            "WHERE f.datum > :date " +
            "GROUP BY f.frischbestand")
    List<FrischBestellung> findByDateAfterAndSum(@Param("date") Timestamp date);

}
