package de.dhbw.foodcoop.warehouse.plugins.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;

public interface SpringDataFrischBestandRepository extends JpaRepository<FrischBestand, String> {
}
