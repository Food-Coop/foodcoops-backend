package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;

public interface SpringDataBrotBestellungRepository extends JpaRepository<BrotBestellung, String>{

    @Query(value = "SELECT b FROM BrotBestellung b WHERE b.datum > :date AND b.person_id = :person_id", nativeQuery = true)
    List<BrotBestellung> findByDateAfterAndPerson(@Param("date") Timestamp date, @Param("person_id") String person_id);

    @Query(value = "SELECT b FROM BrotBestellung b WHERE b.datum <= :date1 AND b.datum > :date2 AND b.person_id = :person_id", nativeQuery = true)
    List<BrotBestellung> findByDateBetween(@Param("date1") Timestamp date1, @Param("date2") Timestamp date2, @Param("person_id") String person_id);

    @Query(value = "SELECT new BrotBestellung(b.id, b.person_id, b.brotbestand,  SUM(b.bestellmenge)) " +
            "FROM BrotBestellung b " +
            "WHERE b.datum > :date " +
            "GROUP BY b.brotbestand", nativeQuery = true)
    List<BrotBestellung> findByDateAfterAndSum(@Param("date") Timestamp date);
}
