package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;

public interface SpringDataBrotBestellungRepository extends JpaRepository<BrotBestellung, String>{

    @Query("SELECT b FROM BrotBestellung b WHERE b.datum > :date AND b.personId = :person_id")
    List<BrotBestellung> findByDateAfterAndPerson(@Param("date") LocalDateTime date, @Param("person_id") String person_id);

    @Query("SELECT b FROM BrotBestellung b WHERE b.datum <= :date1 AND b.datum > :date2 AND b.personId = :person_id")
    List<BrotBestellung> findByDateBetween(@Param("date1") LocalDateTime date1, @Param("date2") LocalDateTime date2, @Param("person_id") String person_id);

    @Query("SELECT new BrotBestellung(b.id, b.personId, b.brotbestand,  SUM(b.bestellmenge)) " +
            "FROM BrotBestellung b " +
            "WHERE b.datum > :date " +
            "GROUP BY b.brotbestand")
    List<BrotBestellung> findByDateAfterAndSum(@Param("date") LocalDateTime date);
    
    @Query("SELECT b FROM BrotBestellung b WHERE b.personId = :person_id")
    List<BrotBestellung> findAllFromPerson(@Param("person_id") String person_id);
    
    @Query("SELECT f FROM BrotBestellung f WHERE f.datum > :date")
    List<BrotBestellung> findAllAfter(@Param("date") LocalDateTime date);
}
