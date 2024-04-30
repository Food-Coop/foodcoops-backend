package de.dhbw.foodcoop.warehouse.plugins.persistence;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;

public interface SpringDataProduktRepository extends JpaRepository<Produkt, String> {

}
