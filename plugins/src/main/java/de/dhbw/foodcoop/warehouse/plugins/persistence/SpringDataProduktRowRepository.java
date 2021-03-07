package de.dhbw.foodcoop.warehouse.plugins.persistence;


import de.dhbw.foodcoop.warehouse.adapters.Row.ProduktRow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProduktRowRepository extends JpaRepository<ProduktRow, String> {
}
