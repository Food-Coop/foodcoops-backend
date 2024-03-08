package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.Person;

public interface SpringDataPersonRepository extends JpaRepository<Person, String>{


}
