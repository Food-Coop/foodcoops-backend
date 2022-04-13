package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;

public interface SpringDataFrischBestellungRepository extends JpaRepository<FrischBestellung, String>{

    @Query("SELECT f FROM FrischBestellung f WHERE f.datum > :date")
    List<FrischBestellung> findByDateAfter(@Param("date") Timestamp date);

    @Query("SELECT new FrischBestellung(f.id, f.frischbestand_id, f.person_id, f.bestellmenge) FROM FrischBestellung f WHERE f.datum > :date GROUP BY f.frischbestand_id")
    List<FrischBestellung> findByDateAfterAndSum(@Param("date") Timestamp date);
    
    //SQL-Statement in MariaDB-> Funktioniert, anders als hier
    //SELECT f.id, f.frischbestand_id, f.person_id, SUM(f.bestellmenge), f.datum FROM frischbestellung f WHERE f.datum > '2022-04-10 00:00:00' GROUP BY f.frischbestand_id;
}
