package de.dhbw.foodcoop.warehouse.plugins.persistence;


import de.dhbw.foodcoop.warehouse.domain.entities.Lagerbestand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataLagerbestandRepository extends JpaRepository<Lagerbestand, String> {
}
