package de.dhbw.foodcoop.warehouse.plugins.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;

public interface SpringDataFrischBestellungRepository extends JpaRepository<FrischBestellung, String>{
}
