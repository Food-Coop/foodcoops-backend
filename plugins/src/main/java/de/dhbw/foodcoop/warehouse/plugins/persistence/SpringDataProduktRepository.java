package de.dhbw.foodcoop.warehouse.plugins.persistence;


import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProduktRepository extends JpaRepository<Produkt, String> {
}
